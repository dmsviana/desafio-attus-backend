package dev.dmsviana.desafioattusbackend.domain;

import dev.dmsviana.desafioattusbackend.controllers.dtos.request.address.InsertAddressRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.address.AddressResponseDto;
import dev.dmsviana.desafioattusbackend.domain.address.Address;
import dev.dmsviana.desafioattusbackend.domain.person.Person;
import dev.dmsviana.desafioattusbackend.exceptions.AddressNotFoundException;
import dev.dmsviana.desafioattusbackend.exceptions.InvalidAddressOwnerException;
import dev.dmsviana.desafioattusbackend.exceptions.PersonNotFoundException;
import dev.dmsviana.desafioattusbackend.repositories.AddressRepository;
import dev.dmsviana.desafioattusbackend.repositories.PersonRepository;
import dev.dmsviana.desafioattusbackend.services.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    public void create_ValidAddress_ReturnsAddressResponseDto() {
        String personId = "personId";
        InsertAddressRequestDto addressRequest = new InsertAddressRequestDto("Street", "12345-678", 123, "City", "State", true);
        Person person = new Person();
        Address address = new Address();
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(modelMapper.map(addressRequest, Address.class)).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(modelMapper.map(address, AddressResponseDto.class)).thenReturn(new AddressResponseDto());

        AddressResponseDto createdAddress = addressService.create(personId, addressRequest);

        assertThat(createdAddress).isNotNull();
    }

    @Test
    public void create_WithInvalidPersonId_ThrowsPersonNotFoundException() {
        String invalidPersonId = "invalid-person-id";
        InsertAddressRequestDto addressRequest = new InsertAddressRequestDto();

        when(personRepository.findById(invalidPersonId)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> addressService.create(invalidPersonId, addressRequest));
    }




    @Test
    public void update_WithInvalidAddressId_ThrowsAddressNotFoundException() {
        String invalidAddressId = "invalid-address-id";
        String personId = "personId";
        InsertAddressRequestDto addressRequest = new InsertAddressRequestDto();

        when(addressRepository.findById(invalidAddressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.update(invalidAddressId, personId, addressRequest));
    }

    @Test
    public void update_WithInvalidPersonId_ThrowsPersonNotFoundException() {
        String addressId = "addressId";
        String invalidPersonId = "invalid-person-id";
        InsertAddressRequestDto addressRequest = new InsertAddressRequestDto();
        Address address = new Address();
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        when(personRepository.findById(invalidPersonId)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> addressService.update(addressId, invalidPersonId, addressRequest));
    }

    @Test
    public void findById_WithValidId_ReturnsAddressResponseDto() {
        String addressId = "addressId";
        Address address = new Address();
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(modelMapper.map(address, AddressResponseDto.class)).thenReturn(new AddressResponseDto());

        AddressResponseDto foundAddress = addressService.findById(addressId);

        assertThat(foundAddress).isNotNull();
    }

    @Test
    public void findById_WithInvalidId_ThrowsAddressNotFoundException() {
        String invalidAddressId = "invalid-address-id";

        when(addressRepository.findById(invalidAddressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.findById(invalidAddressId));
    }

    @Test
    public void findAll_ReturnsListOfAddressResponseDto() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        when(addressRepository.findAll()).thenReturn(addresses);
        when(modelMapper.map(any(Address.class), any())).thenReturn(new AddressResponseDto());

        List<AddressResponseDto> allAddresses = addressService.findAll();

        assertThat(allAddresses).hasSize(1);
    }


    @Test
    public void delete_WithInvalidId_ThrowsEmptyResultDataAccessException() {
        String invalidAddressId = "invalid-address-id";

        doThrow(EmptyResultDataAccessException.class).when(addressRepository).deleteById(invalidAddressId);

        assertThrows(EmptyResultDataAccessException.class, () -> addressService.delete(invalidAddressId));
    }
}
