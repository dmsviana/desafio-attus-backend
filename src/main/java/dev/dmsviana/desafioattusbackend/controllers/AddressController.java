package dev.dmsviana.desafioattusbackend.controllers;


import dev.dmsviana.desafioattusbackend.controllers.dtos.request.address.InsertAddressRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.address.AddressResponseDto;
import dev.dmsviana.desafioattusbackend.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @Operation(description = "This method creates a new address for a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{personId}")
    @ResponseStatus(CREATED)
    public AddressResponseDto create(@PathVariable String personId, @RequestBody @Valid InsertAddressRequestDto addressRequest) {
        return addressService.create(personId, addressRequest);
    }

    @Operation(description = "This method updates an existing address for a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Address or person not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{addressId}/{personId}")
    @ResponseStatus(OK)
    public AddressResponseDto update(@PathVariable String addressId, @PathVariable String personId, @RequestBody @Valid InsertAddressRequestDto addressRequest) {
        return addressService.update(addressId, personId, addressRequest);
    }

    @Operation(description = "This method updates the main address for a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Main address updated successfully"),
            @ApiResponse(responseCode = "404", description = "Address or person not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{addressId}/{personId}")
    @ResponseStatus(OK)
    public void updateMainAddress(@PathVariable String addressId, @PathVariable String personId) {
        addressService.updateMainAddress(addressId, personId);
    }

    @Operation(description = "This method finds an address by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address found successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AddressResponseDto findById(@PathVariable String id) {
        return addressService.findById(id);
    }

    @Operation(description = "This method retrieves all addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All addresses retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @ResponseStatus(OK)
    public List<AddressResponseDto> findAll() {
        return addressService.findAll();
    }

    @Operation(description = "This method deletes an address by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable String id) {
        addressService.delete(id);
    }
}
