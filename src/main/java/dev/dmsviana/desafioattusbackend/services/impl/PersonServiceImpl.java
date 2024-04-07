package dev.dmsviana.desafioattusbackend.services.impl;

import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.InsertPersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.UpdatePersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;
import dev.dmsviana.desafioattusbackend.domain.person.Person;
import dev.dmsviana.desafioattusbackend.exceptions.PersonNotFoundException;
import dev.dmsviana.desafioattusbackend.repositories.PersonRepository;
import dev.dmsviana.desafioattusbackend.services.PersonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper mapper;


    @Override
    @Transactional
    public PersonResponseDto create(InsertPersonRequestDto personRequest) {

        Person createdPerson = mapper.map(personRequest, Person.class);
        createdPerson = personRepository.save(createdPerson);
        return mapper.map(createdPerson, PersonResponseDto.class);
    }

    @Override
    public PersonResponseDto update(String id, UpdatePersonRequestDto personRequest) {

        Person person = personRepository.findById(id)
                                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        person.setFullName(personRequest.getFullName());
        person.setBirthDate(personRequest.getBirthDate());

        Person updatedPerson = personRepository.save(person);
        return mapper.map(updatedPerson, PersonResponseDto.class);
    }

    @Override
    public PersonResponseDto findById(String id) {
        Person person = personRepository.findById(id)
                                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        return mapper.map(person, PersonResponseDto.class);
    }

    @Override
    public List<PersonResponseDto> findAll() {
        List<PersonResponseDto> personsListDto = personRepository.findAll().stream()
                .map(person -> mapper.map(person, PersonResponseDto.class))
                .collect(Collectors.toList());
        return personsListDto;
    }

    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }
}
