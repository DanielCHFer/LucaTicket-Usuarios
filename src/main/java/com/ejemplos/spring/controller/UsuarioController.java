package com.ejemplos.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.spring.adapter.UsuariosAdapter;
import com.ejemplos.spring.model.Usuario;
import com.ejemplos.spring.response.UsuarioResponse;
import com.ejemplos.spring.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuariosAdapter usuarioAdapter;
	
	@Operation(
		summary = "Dar de alta un nuevo usuario",
		description = "Permite crear un nuevo usuario en la base de datos. Ignora el Id_usuario si se especifica en el Json de entrada."
	)
	@PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioResponse usuarioResponse) {
			
			//Se ignora el id si se envía
			if (usuarioResponse.getId_usuario() != null) {
				usuarioResponse.setId_usuario(null);
			}
			
			// Se guarda el Usuario covertido a Entidad
			UsuarioResponse res = usuarioAdapter.of(usuarioService.saveUsuario(usuarioAdapter.of(usuarioResponse)).get());
			
			//Se construye la ResponseEntity con el código 201
	        return ResponseEntity
	        		.status(HttpStatus.CREATED)
	        		.body(res);
    }
}
