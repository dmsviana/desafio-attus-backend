package dev.dmsviana.desafioattusbackend.web;


import dev.dmsviana.desafioattusbackend.common.PersonUtil;
import dev.dmsviana.desafioattusbackend.common.Utils;
import dev.dmsviana.desafioattusbackend.controllers.AddressController;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.address.InsertAddressRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.address.AddressResponseDto;
import dev.dmsviana.desafioattusbackend.domain.person.Person;
import dev.dmsviana.desafioattusbackend.services.AddressService;
import dev.dmsviana.desafioattusbackend.services.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

    public static final String BASE_URL = "/api/addresses";

    public static final String ID = PersonUtil.createPersonDefault().getId();

    public static final String ID_URL = BASE_URL + "/" + ID;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressServiceImpl addressService;


    @Test
    public void findAllAddressesSuccess() throws Exception {

        List<AddressResponseDto> responseDTOS = new ArrayList<>();

       responseDTOS.add(new AddressResponseDto("1", "Rua 1", "12345-678", 123, "Cidade 1", "Estado 1", true));

       when(addressService.findAll()).thenReturn(responseDTOS);

       var result =
               mockMvc.perform(get(BASE_URL)
                       .accept(MediaType.APPLICATION_JSON)
                       .contentType(MediaType.APPLICATION_JSON))
                       .andReturn();
       var response = result.getResponse();

       assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
