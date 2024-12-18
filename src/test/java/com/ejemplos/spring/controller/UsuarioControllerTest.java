package com.ejemplos.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.ejemplos.spring.adapter.UsuariosAdapter;
import com.ejemplos.spring.model.Usuario;
import com.ejemplos.spring.response.UsuarioResponse;
import com.ejemplos.spring.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

	
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuariosAdapter usuarioAdapter;
    
    @Test
	void shouldPostEventoDevuelveJson() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    	
    	UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setNombre("Juan");
        usuarioResponse.setApellido("Perez");
        usuarioResponse.setEmail("email@ejemplo.com");  // email MAL
        usuarioResponse.setFecha_alta(LocalDate.of(2024, 12, 15));
    	
	    // String usuarioJson = "{\"nombre\": \"Usuario\", \"apellido\": \"Apellido\", \"email\": \"mail@ejemplo.com\", \"fecha_alta\": \"2024-12-11\"}";

     // Configurar mocks
        Usuario usuarioMock = new Usuario();
        usuarioMock.setNombre("Juan");
        usuarioMock.setApellido("Perez");
        usuarioMock.setEmail("email@ejemplo.com");
        usuarioMock.setFecha_alta(LocalDate.of(2024, 12, 15));
        
        Mockito.when(usuarioAdapter.of(Mockito.any(UsuarioResponse.class)))
               .thenReturn(usuarioMock);
        
        Mockito.when(usuarioService.saveUsuario(Mockito.any(Usuario.class)))
               .thenReturn(Optional.of(usuarioMock));
        
        Mockito.when(usuarioAdapter.of(Mockito.any(Usuario.class)))
               .thenReturn(usuarioResponse);
        
        
        
	    mockMvc.perform(post("/usuarios")
	            .contentType(MediaType.APPLICATION_JSON)  
	            .content(objectMapper.writeValueAsString(usuarioResponse))) 
	            .andDo(print())  
	            .andExpect(status().isCreated())  
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
	}
    
    @Test
    public void shouldTestCrearUsuarioExitoso() throws Exception {
    	
        // Crear UsuarioResponse de entrada
        UsuarioResponse usuarioRequest = new UsuarioResponse();
        usuarioRequest.setNombre("Juan");
        usuarioRequest.setApellido("Perez");
        usuarioRequest.setEmail("juan.perez@example.com");
        usuarioRequest.setFecha_alta(LocalDate.of(2024, 12, 11));
        
        // Crear un objeto simulado devuelto por el servicio
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId_usuario(1L); // Este ID se generará al persistir
        usuarioResponse.setNombre("Juan");
        usuarioResponse.setApellido("Perez");
        usuarioResponse.setEmail("juan.perez@example.com");
        usuarioResponse.setFecha_alta(LocalDate.of(2024, 12, 11));

        // Simula conversión a entidad
        Mockito.when(usuarioAdapter.of(Mockito.any(UsuarioResponse.class)))
                .thenReturn(new Usuario()); 

        // Simula guardado en base de datos
        Mockito.when(usuarioService.saveUsuario(Mockito.any(Usuario.class)))
                .thenReturn(Optional.of(new Usuario())); 

        // Simula conversión de entidad a respuesta
        Mockito.when(usuarioAdapter.of(Mockito.any(Usuario.class)))
                .thenReturn(usuarioResponse); 

        // Configurar ObjectMapper para manejar LocalDate
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LA FECHA

        String usuarioRequestJson = objectMapper.writeValueAsString(usuarioRequest); // Convertir el objeto de entrada a JSON
        
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioRequestJson))
                .andExpect(status().isCreated()) // Verifica el código 201
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"))
                .andExpect(jsonPath("$.fecha_alta").value("2024-12-11"));
    }
    
    @Test
    public void shouldTestUsuarioFormatoNoValido() throws Exception {

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
    
    
    @Test
    public void shouldRestResponseEntityMismoFormato() throws Exception {
    	
        // Crear UsuarioResponse de entrada
        UsuarioResponse usuarioRequest = new UsuarioResponse();
        usuarioRequest.setNombre("Juan");
        usuarioRequest.setApellido("Perez");
        usuarioRequest.setEmail("juan.perez@example.com");
        usuarioRequest.setFecha_alta(LocalDate.of(2024, 12, 11));
        
        // Crear un objeto simulado devuelto por el servicio
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId_usuario(1L); // Este ID se generará al persistir
        usuarioResponse.setNombre("Juan");
        usuarioResponse.setApellido("Perez");
        usuarioResponse.setEmail("juan.perez@example.com");
        usuarioResponse.setFecha_alta(LocalDate.of(2024, 12, 11));

        // Simula conversión a entidad
        Mockito.when(usuarioAdapter.of(Mockito.any(UsuarioResponse.class)))
                .thenReturn(new Usuario()); 

        // Simula guardado en base de datos
        Mockito.when(usuarioService.saveUsuario(Mockito.any(Usuario.class)))
                .thenReturn(Optional.of(new Usuario())); 

        // Simula conversión de entidad a respuesta
        Mockito.when(usuarioAdapter.of(Mockito.any(Usuario.class)))
                .thenReturn(usuarioResponse); 

        // Configurar ObjectMapper para manejar LocalDate
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LA FECHA

        String usuarioRequestJson = objectMapper.writeValueAsString(usuarioRequest); // Convertir el objeto de entrada a JSON
        
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioRequestJson))
                .andExpect(status().isCreated()) // Verifica el código 201
                .andExpect(jsonPath("$.id_usuario").exists())
                .andExpect(jsonPath("$.nombre").exists())
                .andExpect(jsonPath("$.apellido").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.fecha_alta").exists());
    }
    
    
	@Test
	void shouldGetEventoDevuelveJson() throws Exception {
        Usuario usuario = new Usuario(1L, "Adrian", "Gomez", "adriangomezc12@gmail.com", LocalDate.of(2024, 12, 12));
        Optional<Usuario> res = Optional.of(usuario);
        when(usuarioService.findByEmail("adriangomezc12@gmail.com")).thenReturn(res);

        mockMvc.perform(get("/usuarios/adriangomezc12@gmailcom"))
		   .andDo(print())
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
    
    
}

