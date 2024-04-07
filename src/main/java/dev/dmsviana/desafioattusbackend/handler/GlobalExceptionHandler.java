package dev.dmsviana.desafioattusbackend.handler;


import dev.dmsviana.desafioattusbackend.exceptions.AddressNotFoundException;
import dev.dmsviana.desafioattusbackend.exceptions.InvalidAddressOwnerException;
import dev.dmsviana.desafioattusbackend.exceptions.PersonNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleBadRequestException(BadRequestException ex) {
        return new ApiErrors(ex.getMessage());
    }


    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlePersonNotFoundException(PersonNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlePersonNotFoundException(AddressNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(InvalidAddressOwnerException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handlePersonNotFoundException(InvalidAddressOwnerException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
