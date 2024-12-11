package com.ejemplos.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.ejemplos.spring.adapter.UsuariosAdapter;
import com.ejemplos.spring.response.UsuarioResponse;
import com.ejemplos.spring.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

//@SpringBootTest
//@AutoConfigureMockMvc  // Configurar automáticamente MockMvc
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuariosAdapter usuarioAdapter;

    @Test
    public void testUsuarioFormatoNoValido() throws Exception {

    	// Configurar ObjectMapper para manejar LocalDate
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setNombre("Juan");
        usuarioResponse.setApellido("Perez");
        usuarioResponse.setEmail("email_invalido");  // email MAL
        usuarioResponse.setFecha_alta(LocalDate.of(2024, 12, 15));

        mockMvc.perform(post("/usuarios")  // Cambia la URL según sea necesario
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioResponse)))  // Usa el objectMapper configurado
        		//.andDo(print())  // Esto imprime la respuesta en la consola
                .andExpect(status().isBadRequest())  // Verifica que se devuelve un 400 Bad Request
                .andExpect(jsonPath("$.message[0]").value("email: El email debe tener un formato válido"));  // Verifica el mensaje de error
    }
}

