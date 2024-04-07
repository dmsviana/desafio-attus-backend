package dev.dmsviana.desafioattusbackend.controllers;


import dev.dmsviana.desafioattusbackend.controllers.dtos.request.address.InsertAddressRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.address.AddressResponseDto;
import dev.dmsviana.desafioattusbackend.services.AddressService;
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

    @PostMapping("/{personId}")
    @ResponseStatus(CREATED)
    public AddressResponseDto create(@PathVariable String personId, @RequestBody @Valid InsertAddressRequestDto addressRequest) {
        return addressService.create(personId, addressRequest);
    }

    @PutMapping("/{addressId}/{personId}")
    @ResponseStatus(OK)
    public AddressResponseDto update(@PathVariable String addressId, @PathVariable String personId, @RequestBody @Valid InsertAddressRequestDto addressRequest) {
        return addressService.update(addressId, personId, addressRequest);
    }

    @PatchMapping("/{addressId}/{personId}")
    @ResponseStatus(OK)
    public void updateMainAddress(@PathVariable String addressId, @PathVariable String personId) {
        addressService.updateMainAddress(addressId, personId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AddressResponseDto findById(@PathVariable String id) {
        return addressService.findById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<AddressResponseDto> findAll() {
        return addressService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable String id) {
        addressService.delete(id);
    }
}
