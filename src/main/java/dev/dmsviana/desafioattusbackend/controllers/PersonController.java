package dev.dmsviana.desafioattusbackend.controllers;


import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.InsertPersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.UpdatePersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;
import dev.dmsviana.desafioattusbackend.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(CREATED)
    public PersonResponseDto create(@RequestBody @Valid InsertPersonRequestDto personRequest) {
        return personService.create(personRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public PersonResponseDto update(@PathVariable String id, @RequestBody @Valid UpdatePersonRequestDto personRequest){
        return personService.update(id, personRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public PersonResponseDto findById(@PathVariable String id){
        return personService.findById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<PersonResponseDto> findAll(){
        return personService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable String id){
        personService.delete(id);
    }
}
