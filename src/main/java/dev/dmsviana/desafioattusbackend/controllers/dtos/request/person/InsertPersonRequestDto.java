package dev.dmsviana.desafioattusbackend.controllers.dtos.request.person;

import dev.dmsviana.desafioattusbackend.controllers.dtos.request.address.InsertAddressRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertPersonRequestDto {

        @NotBlank(message = "The field full name cannot be blank.")
        private String fullName;

        @NotNull(message = "The field birth date cannot be blank.")
        private LocalDate birthDate;

        private List<InsertAddressRequestDto>addressList;
}