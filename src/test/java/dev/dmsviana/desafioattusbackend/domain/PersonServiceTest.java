package dev.dmsviana.desafioattusbackend.domain;

import dev.dmsviana.desafioattusbackend.common.PersonUtil;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.InsertPersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.UpdatePersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;
import dev.dmsviana.desafioattusbackend.domain.person.Person;
import dev.dmsviana.desafioattusbackend.exceptions.PersonNotFoundException;
import dev.dmsviana.desafioattusbackend.repositories.PersonRepository;
import dev.dmsviana.desafioattusbackend.services.impl.PersonServiceImpl;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;


import java.util.ArrayList;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void findPersonById_WithValidId_ReturnsPerson() {

        Person person = PersonUtil.createPersonDefault();
        String id = person.getId();

        PersonResponseDto expectedResponse = PersonUtil.createExpectedResponseDefault();

        when(personRepository.findById(any())).thenReturn(Optional.of(person));

        PersonResponseDto response = personService.findById(id);

        assertAll("response",
                () -> assertEquals(id, response.getId()),
                () -> assertEquals(expectedResponse.getFullName(), response.getFullName()),
                () -> assertEquals(expectedResponse.getBirthDate(), response.getBirthDate())
                );
        verify(personRepository).findById(id);
    }

    @Test
    public void findPersonById_WithInvalidId_ThrowsPersonNotFoundException() {

        when(personRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.findById("1"));
        verify(personRepository).findById("1");
    }

    @Test
    public void createPerson_WithValidData_ReturnsPerson() {
        InsertPersonRequestDto personRequest = new InsertPersonRequestDto();
        Person person = PersonUtil.createPersonDefault();
        String id = person.getId();

        when(personRepository.save(any())).thenReturn(person);

        PersonResponseDto expectedResponse = PersonUtil.createExpectedResponseDefault();

        PersonResponseDto response = personService.create(personRequest);

        assertAll("response",
                () -> assertEquals(id, response.getId()),
                () -> assertEquals(expectedResponse.getFullName(), response.getFullName()),
                () -> assertEquals(expectedResponse.getBirthDate(), response.getBirthDate())
                );
        verify(personRepository).save(any(Person.class));
    }

    @Test
    public void createCustomer_WithInvalidData_ThrowsException() {
        InsertPersonRequestDto personRequest = new InsertPersonRequestDto();
        personRequest.setFullName(null);
        personRequest.setBirthDate(null);

        assertThrows(IllegalArgumentException.class, () -> personService.create(personRequest));
        verify(personRepository).save(any(Person.class));
    }

    @Test
    public void updatePerson_WithValidData_ReturnsPerson() {
        UpdatePersonRequestDto personRequest = new UpdatePersonRequestDto("Diogo", LocalDate.of(1996, 10, 10));
        Person person = PersonUtil.createPersonDefault();
        String id = person.getId();

        PersonResponseDto expectedResponse = PersonUtil.createExpectedResponseDefault();

        when(personRepository.findById(anyString())).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonResponseDto response = personService.update(id, personRequest);

        assertAll("response",
                () -> assertEquals(id, response.getId()),
                () -> assertEquals(expectedResponse.getFullName(), response.getFullName()),
                () -> assertEquals(expectedResponse.getBirthDate(), response.getBirthDate())
                );
        verify(personRepository).findById(id);
        verify(personRepository).save(person);
    }

    @Test
    public void updatePerson_WithInvalidData_ThrowsException() {
        when(personRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.findById("1"));
        verify(personRepository).findById("1");
    }

    @Test
    public void deletePerson_WithValidId_DeletesPerson() {
        Person person = PersonUtil.createPersonDefault();
        String id = person.getId();

        doNothing().when(personRepository).deleteById(id);

        personService.delete(id);

        verify(personRepository).deleteById(id);
    }

    @Test
    public void deletePerson_WithInvalidId_ThrowsException() {
        doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById("1");

        assertThrows(EmptyResultDataAccessException.class, () -> personService.delete("1"));
        verify(personRepository).deleteById("1");
    }



}