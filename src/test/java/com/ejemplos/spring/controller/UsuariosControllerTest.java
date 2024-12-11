package com.ejemplos.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ejemplos.spring.service.UsuarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuariosControllerTest {

	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	void shouldPostResponseEntity() throws Exception {
		
		String nombre = "nombre de prueba";
		String apellido = "apellido de prueba";
		String email = "emaildeprueba@gmail.com";
		String fecha = "2024-06-12";
		
	    
	    String eventoJson = "{\"nombre\": \""+nombre+"\", \"apellido\": \""+apellido+"\", \"email\": \""+email+"\", \"fecha_alta\": "
	    		+ "\""+fecha+"\"}";

	    mockMvc.perform(post("/usuarios")
	            .contentType(MediaType.APPLICATION_JSON)  
	            .content(eventoJson))  
	            .andDo(print())  
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.nombre").value(nombre))
	            .andExpect(jsonPath("$.apellido").value(apellido))
	            .andExpect(jsonPath("$.email").value(email))
	            .andExpect(jsonPath("$.fecha_alta").value(fecha));
	}
	
}
