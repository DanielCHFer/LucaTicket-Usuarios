package com.ejemplos.spring.controller.error;

public class UsuarioFormatException extends RuntimeException{

	public UsuarioFormatException(String message) {
		super(message);
	}
	
}
