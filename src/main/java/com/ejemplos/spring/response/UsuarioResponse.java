package com.ejemplos.spring.response;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id_usuario;
	
	@NotBlank(message = "El nombre del usuario no puede estar vacío")
	private String nombre;
	
	@NotBlank(message = "El apellido del usuario no puede estar vacío.")
	private String apellido;
	
	@NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
	private String email;
	
	@NotNull(message = "La fecha de alta no puede estar vacía")
	private LocalDate fecha_alta;

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(LocalDate fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
