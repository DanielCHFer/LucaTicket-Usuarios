package com.ejemplos.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.spring.model.Usuario;
import com.ejemplos.spring.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> saveUsuario(Usuario usuario){
		return Optional.of(usuarioRepository.save(usuario));
	}
	
	
}
