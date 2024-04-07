package dev.dmsviana.desafioattusbackend.exceptions;

public class InvalidAddressOwnerException extends RuntimeException {

    public InvalidAddressOwnerException(String message) {
        super(message);
    }
}
