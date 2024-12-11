package com.ejemplos.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.spring.adapter.UsuariosAdapter;
import com.ejemplos.spring.model.Usuario;
import com.ejemplos.spring.response.UsuarioResponse;
import com.ejemplos.spring.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuariosAdapter usuarioAdapter;
	
	@PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioResponse usuarioResponse) {
			if (usuarioResponse.getId_usuario() != null) {
				usuarioResponse.setId_usuario(null);
			}
			
			Usuario usuario = usuarioService.saveUsuario(usuarioAdapter.of(usuarioResponse)).get();
	        return ResponseEntity.ok(usuario);

    }
}
