package dev.dmsviana.desafioattusbackend.controllers.dtos.request.address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertAddressRequestDto {

    @NotBlank(message = "The field street cannot be blank")
    private String street;

    @NotBlank(message = "The field zipCode cannot be blank")
    private String zipCode;

    @NotNull(message = "The field number cannot be blank")
    private Integer number;

    @NotBlank(message = "The field city cannot be blank")
    private String city;

    @NotBlank(message = "The field state cannot be blank")
    private String state;

    private Boolean mainAddress;

}
