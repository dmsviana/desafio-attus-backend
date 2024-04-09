package dev.dmsviana.desafioattusbackend.web;
import dev.dmsviana.desafioattusbackend.controllers.PersonController;
import dev.dmsviana.desafioattusbackend.controllers.dtos.request.person.InsertPersonRequestDto;
import dev.dmsviana.desafioattusbackend.controllers.dtos.response.person.PersonResponseDto;
import dev.dmsviana.desafioattusbackend.services.impl.PersonServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonServiceImpl personService;

    @Test
    public void createPerson_WithValidData_ReturnsCreated() throws Exception {
        String id = UUID.randomUUID().toString();
        InsertPersonRequestDto requestDto = new InsertPersonRequestDto();
        requestDto.setFullName("Diogo Marcelo");
        requestDto.setBirthDate(LocalDate.of(1990, 1, 1));

        PersonResponseDto responseDto = new PersonResponseDto();
        responseDto.setId(id);
        responseDto.setFullName("Diogo Marcelo");
        responseDto.setBirthDate(LocalDate.of(1990, 1, 1));

        when(personService.create(any(InsertPersonRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.fullName", is("Diogo Marcelo")))
                .andExpect(jsonPath("$.birthDate", is("1990-01-01")));

        verify(personService).create(any(InsertPersonRequestDto.class));
    }

    @Test
    public void createPerson_WithInvalidData_ReturnsBadRequest() throws Exception {
        InsertPersonRequestDto invalidRequest = new InsertPersonRequestDto();
        invalidRequest.setFullName("");
        invalidRequest.setBirthDate(null);

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }






}
