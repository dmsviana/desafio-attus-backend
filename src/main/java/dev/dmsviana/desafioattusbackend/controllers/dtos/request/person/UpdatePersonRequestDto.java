package dev.dmsviana.desafioattusbackend.controllers.dtos.request.person;

import dev.dmsviana.desafioattusbackend.domain.address.Address;
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
public class UpdatePersonRequestDto {

    @NotBlank(message = "The field name cannot be blank.")
    private String fullName;
    @NotNull(message = "The field birthDate cannot be null.")
    private LocalDate birthDate;
}
