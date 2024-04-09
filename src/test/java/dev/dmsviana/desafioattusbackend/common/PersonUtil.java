package dev.dmsviana.desafioattusbackend.common;

import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;
import dev.dmsviana.desafioattusbackend.domain.person.Person;

import java.time.LocalDate;
import java.util.UUID;

public class PersonUtil {

    public static Person createPersonDefault(){
        Person person = new Person(UUID.randomUUID().toString(), "Diogo", LocalDate.of(1996, 10, 10), null);
        return person;
    }

    public static PersonResponseDto createExpectedResponseDefault() {
        return new PersonResponseDto(UUID.randomUUID().toString(), "Diogo", LocalDate.of(1996, 10, 10), null);
    }
}
