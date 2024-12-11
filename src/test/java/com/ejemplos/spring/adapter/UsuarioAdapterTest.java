package com.ejemplos.spring.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;


import com.ejemplos.spring.model.Usuario;
import com.ejemplos.spring.response.UsuarioResponse;


public class UsuarioAdapterTest {
	
	@Test
	void shouldNombreEventoResponseIgualANombreEvento() {
		
		//en usuario adapter hago la conversion de usuario a usuarioResponse y compruebo que lo que me devuelve esta correcto
		UsuariosAdapter adapter = new UsuariosAdapter();
		
		Usuario usuario = new Usuario();
		
		usuario.setNombre("Prueba");
        usuario.setApellido("prueba");
        usuario.setEmail("prueba@gmail.com");
        usuario.setFecha_alta(LocalDate.of(2024, 12, 15));
        
        
        UsuarioResponse usuarioResponse = adapter.of(usuario);
        
        assertEquals(usuario.getNombre(), usuarioResponse.getNombre());
	}
	
	@Test
	void shouldNombreEventoIgualANombreEventoResponse() {
		
		//en usuario adapter hago la conversion de usuarioResponse a usuario y compruebo que lo que me devuelve esta correcto
		
		UsuariosAdapter adapter = new UsuariosAdapter();
		
		UsuarioResponse usuarioResponse = new UsuarioResponse();
		
		usuarioResponse.setNombre("Prueba");
		usuarioResponse.setApellido("prueba");
		usuarioResponse.setEmail("prueba@gmail.com");
		usuarioResponse.setFecha_alta(LocalDate.of(2024, 12, 15));
        
        Usuario usuario = adapter.of(usuarioResponse);
        
        assertEquals(usuarioResponse.getNombre(), usuario.getNombre());
	}

}
