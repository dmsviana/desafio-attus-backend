package dev.dmsviana.desafioattusbackend.services;

import dev.dmsviana.desafioattusbackend.controllers.dtos.request.address.InsertAddressRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.address.AddressResponseDto;

import java.util.List;

public interface AddressService {
    AddressResponseDto create(String personId, InsertAddressRequestDto addressRequest);
    AddressResponseDto update(String addressId, String personId, InsertAddressRequestDto addressRequest);

    AddressResponseDto findById(String id);
    List<AddressResponseDto> findAll();

    void updateMainAddress(String addressId, String personId);
    void delete(String id);
}
