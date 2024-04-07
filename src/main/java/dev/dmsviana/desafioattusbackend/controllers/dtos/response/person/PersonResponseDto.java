package dev.dmsviana.desafioattusbackend.controllers.dtos.response.person;


import dev.dmsviana.desafioattusbackend.controllers.dtos.response.address.AddressResponseDto;
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
public class PersonResponseDto {

    private String id;
    private String fullName;
    private LocalDate birthDate;
    private List<AddressResponseDto> addressList;
}
