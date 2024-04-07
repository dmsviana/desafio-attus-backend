package dev.dmsviana.desafioattusbackend.controllers.dtos.response.address;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {

    private String id;
    private String street;
    private String zipCode;
    private Integer number;
    private String city;
    private String state;
    private Boolean mainAddress;
}
