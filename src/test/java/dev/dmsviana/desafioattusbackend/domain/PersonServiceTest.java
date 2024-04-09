package dev.dmsviana.desafioattusbackend.domain;

import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.InsertPersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.UpdatePersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;
import dev.dmsviana.desafioattusbackend.domain.person.Person;
import dev.dmsviana.desafioattusbackend.exceptions.PersonNotFoundException;
import dev.dmsviana.desafioattusbackend.repositories.PersonRepository;
import dev.dmsviana.desafioattusbackend.services.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;


import java.util.ArrayList;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person testPerson;
    private String testPersonId;

    @BeforeEach
    public void setUp() {
        testPersonId = UUID.randomUUID().toString();
        testPerson = new Person();
        testPerson.setId(testPersonId);
        testPerson.setFullName("Test Person");
        testPerson.setBirthDate(LocalDate.of(1990, 1, 1));
    }

    @Test
    public void create_WithValidData_ReturnsPersonResponseDto() {
        InsertPersonRequestDto personRequest = new InsertPersonRequestDto();
        personRequest.setFullName("Test Person");
        personRequest.setBirthDate(LocalDate.of(1990, 1, 1));

        when(mapper.map(any(InsertPersonRequestDto.class), any())).thenReturn(testPerson);
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);
        when(mapper.map(any(Person.class), any())).thenReturn(new PersonResponseDto(testPerson.getId(), testPerson.getFullName(), testPerson.getBirthDate(), List.of()));

        PersonResponseDto createdPerson = personService.create(personRequest);

        assertThat(createdPerson.getId()).isEqualTo(testPersonId);
        assertThat(createdPerson.getFullName()).isEqualTo("Test Person");
        assertThat(createdPerson.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    public void update_WithValidData_ReturnsUpdatedPersonResponseDto() {
        String updatedName = "Updated Test Person";
        LocalDate updatedBirthDate = LocalDate.of(1991, 2, 2);
        UpdatePersonRequestDto updateRequest = new UpdatePersonRequestDto();
        updateRequest.setFullName(updatedName);
        updateRequest.setBirthDate(updatedBirthDate);

        when(personRepository.findById(testPersonId)).thenReturn(Optional.of(testPerson));

        Person updatedPerson = new Person();
        updatedPerson.setId(testPersonId);
        updatedPerson.setFullName(updatedName);
        updatedPerson.setBirthDate(updatedBirthDate);

        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);
        when(mapper.map(any(Person.class), any())).thenReturn(new PersonResponseDto(updatedPerson.getId(), updatedPerson.getFullName(), updatedPerson.getBirthDate(), List.of()));

        PersonResponseDto updatedPersonResponse = personService.update(testPersonId, updateRequest);

        assertThat(updatedPersonResponse.getId()).isEqualTo(testPersonId);
        assertThat(updatedPersonResponse.getFullName()).isEqualTo(updatedName);
        assertThat(updatedPersonResponse.getBirthDate()).isEqualTo(updatedBirthDate);
    }

    @Test
    public void findById_WithValidId_ReturnsPersonResponseDto() {
        when(personRepository.findById(testPersonId)).thenReturn(Optional.of(testPerson));
        when(mapper.map(any(Person.class), any())).thenReturn(new PersonResponseDto(testPerson.getId(), testPerson.getFullName(), testPerson.getBirthDate(), List.of()));

        PersonResponseDto foundPerson = personService.findById(testPersonId);

        assertThat(foundPerson.getId()).isEqualTo(testPersonId);
        assertThat(foundPerson.getFullName()).isEqualTo("Test Person");
        assertThat(foundPerson.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    public void findAll_ReturnsListOfPersonResponseDto() {
        List<Person> testPersons = new ArrayList<>();
        testPersons.add(testPerson);

        when(personRepository.findAll()).thenReturn(testPersons);
        when(mapper.map(any(Person.class), any())).thenReturn(new PersonResponseDto(testPerson.getId(), testPerson.getFullName(), testPerson.getBirthDate(), List.of()));

        List<PersonResponseDto> allPersons = personService.findAll();

        assertThat(allPersons).hasSize(1);
        assertThat(allPersons.get(0).getId()).isEqualTo(testPersonId);
        assertThat(allPersons.get(0).getFullName()).isEqualTo("Test Person");
        assertThat(allPersons.get(0).getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    public void delete_WithValidId_DeletesSuccessfully() {
        personService.delete(testPersonId);

        verify(personRepository).deleteById(testPersonId);
    }

    @Test
    public void findById_WithInvalidId_ThrowsPersonNotFoundException() {
        String invalidId = "invalid-id";

        when(personRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidId));
    }

    @Test
    public void update_WithInvalidId_ThrowsPersonNotFoundException() {
        String invalidId = "invalid-id";
        String updatedName = "Updated Test Person";
        LocalDate updatedBirthDate = LocalDate.of(1991, 2, 2);
        UpdatePersonRequestDto updateRequest = new UpdatePersonRequestDto();
        updateRequest.setFullName(updatedName);
        updateRequest.setBirthDate(updatedBirthDate);

        when(personRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.update(invalidId, updateRequest));
    }



    @Test
    public void delete_WithInvalidId_ThrowsEmptyResultDataAccessException() {
        String invalidId = "invalid-id";
        doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(invalidId);
        assertThrows(EmptyResultDataAccessException.class, () -> personService.delete(invalidId));
    }





}