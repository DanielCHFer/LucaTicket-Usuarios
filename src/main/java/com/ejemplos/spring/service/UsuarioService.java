package com.ejemplos.spring.service;

import java.util.Optional;

import com.ejemplos.spring.model.Usuario;

public interface UsuarioService {

	public Optional<Usuario> saveUsuario(Usuario usuario);
	
}
