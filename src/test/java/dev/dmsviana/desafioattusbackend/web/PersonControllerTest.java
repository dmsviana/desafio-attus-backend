package dev.dmsviana.desafioattusbackend.web;
import dev.dmsviana.desafioattusbackend.common.Utils;
import dev.dmsviana.desafioattusbackend.controllers.PersonController;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.InsertPersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;
import dev.dmsviana.desafioattusbackend.exceptions.PersonNotFoundException;
import dev.dmsviana.desafioattusbackend.services.impl.PersonServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    public static final String BASE_URL = "/api/persons";
    public static final String ID = UUID.randomUUID().toString();

    public static final String ID_URL = BASE_URL + "/" + ID;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;


    @Test
    public void findPersonById_WithValidId_ReturnsPerson() throws Exception {
        when(personService.findById(anyString())).thenReturn(new PersonResponseDto());

        var result =
                mockMvc.perform(get(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void findPersonById_WithInvalidId_ReturnsPersonNotFoundException() throws Exception {
        when(personService.findById(anyString())).thenThrow(new PersonNotFoundException("Person not found."));

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void createPerson_WithValidData_ReturnsCreated() throws Exception {

        InsertPersonRequestDto requestDto = new InsertPersonRequestDto();
        requestDto.setFullName("Diogo Marcelo");
        requestDto.setBirthDate(LocalDate.of(1990, 1, 1));

        PersonResponseDto responseDto = new PersonResponseDto();
        responseDto.setId(ID);
        responseDto.setFullName("Diogo Marcelo");
        responseDto.setBirthDate(LocalDate.of(1990, 1, 1));

        when(personService.create(any(InsertPersonRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.mapToString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.fullName", is("Diogo Marcelo")))
                .andExpect(jsonPath("$.birthDate", is("1990-01-01")));

        verify(personService).create(any(InsertPersonRequestDto.class));
    }

    @Test
    public void createPerson_WithInvalidData_ReturnsBadRequest() throws Exception {
        InsertPersonRequestDto invalidRequest = new InsertPersonRequestDto();
        invalidRequest.setFullName("");
        invalidRequest.setBirthDate(null);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.mapToString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }


}
