package dev.dmsviana.desafioattusbackend.services;

import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.InsertPersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.UpdatePersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;

import java.util.List;

public interface PersonService {

    PersonResponseDto create(InsertPersonRequestDto personRequest);
    PersonResponseDto update(String id, UpdatePersonRequestDto personRequest);
    PersonResponseDto findById(String id);

    List<PersonResponseDto> findAll();
    void delete(String id);

}
