package dev.dmsviana.desafioattusbackend.services.impl;

import dev.dmsviana.desafioattusbackend.controllers.dtos.request.address.InsertAddressRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.address.AddressResponseDto;
import dev.dmsviana.desafioattusbackend.domain.address.Address;
import dev.dmsviana.desafioattusbackend.domain.person.Person;
import dev.dmsviana.desafioattusbackend.exceptions.AddressNotFoundException;
import dev.dmsviana.desafioattusbackend.exceptions.InvalidAddressOwnerException;
import dev.dmsviana.desafioattusbackend.exceptions.PersonNotFoundException;
import dev.dmsviana.desafioattusbackend.repositories.AddressRepository;
import dev.dmsviana.desafioattusbackend.repositories.PersonRepository;
import dev.dmsviana.desafioattusbackend.services.AddressService;
import dev.dmsviana.desafioattusbackend.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Override
    public AddressResponseDto create(String personId, InsertAddressRequestDto addressRequest) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        Address address = modelMapper.map(addressRequest, Address.class);
        address.setPerson(person);
        address.setMainAddress(person.getAddress().isEmpty());
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressResponseDto.class);
    }

    @Override
    public AddressResponseDto update(String addressId, String personId, InsertAddressRequestDto addressRequest) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        validate(address, personId);

        address.setStreet(addressRequest.getStreet());
        address.setZipCode(addressRequest.getZipCode());
        address.setNumber(addressRequest.getNumber());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        Address updatedAddress = addressRepository.save(address);
        return modelMapper.map(updatedAddress, AddressResponseDto.class);
    }

    @Override
    public AddressResponseDto findById(String id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        return modelMapper.map(address, AddressResponseDto.class);
    }

    @Override
    public List<AddressResponseDto> findAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressResponseDto.class))
                .toList();
    }

    @Override
    public void updateMainAddress(String addressId, String personId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));

        validate(address, personId);
        address.setMainAddress(true);
        addressRepository.save(address);

        List<Address> addresses = addressRepository.findByPersonId(personId);
        for (Address a : addresses) {
            if (!a.getId().equals(addressId)) {
                a.setMainAddress(false);
                addressRepository.save(a);
            }
        }
    }
    @Override
    public void delete(String id) {
        addressRepository.deleteById(id);
    }


    private void validate(Address address, String personId) {
        if (!address.getPerson().getId().equals(personId)) {
            throw new InvalidAddressOwnerException("The address does not belong to the specified person.");
        }
    }
}
