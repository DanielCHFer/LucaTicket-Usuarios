package com.ejemplos.spring.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;

public class UsuariosAdapter {

	public UsuarioResponse of(Usuario usuario) {

		UsuarioResponse dto = new UsuarioResponse();
		
		dto.setId_usuario(usuario.getId_usuario());
		dto.setNombre(usuario.getNombre());
		dto.setApellido(usuario.getApellido());
		dto.setEmail(usuario.getEmail());
		dto.setFecha_alta(usuario.getFecha_alta());

		return dto;
		
	}

	public List<UsuarioResponse> of(List<Usuario> usuarios) {

		List<UsuarioResponse> response = new ArrayList<>();

		for (Usuario e : usuarios)
			response.add(this.of(e));

		return response;
	}
	
	public Usuario of(UsuarioResponse usuarioResponse) {

		Usuario usuario = new Usuario();
		
		usuario.setId_usuario(usuarioResponse.getId_usuario());
		usuario.setNombre(usuarioResponse.getNombre());
		usuario.setApellido(usuarioResponse.getApellido());
		usuario.setEmail(usuarioResponse.getEmail());
		usuario.setFecha_alta(usuarioResponse.getFecha_alta());
		

		return usuario;
		
	}

	public List<Usuario> ofUsuarios(List<UsuarioResponse> responses) {
		List<Usuario> usuarios = new ArrayList<>();
		
		for (UsuarioResponse e : responses) {
			usuarios.add(this.of(e));
		}
		
		return usuarios;
	}
	
}
