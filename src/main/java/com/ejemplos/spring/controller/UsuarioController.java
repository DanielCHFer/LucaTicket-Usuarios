package com.ejemplos.spring.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.spring.adapter.UsuariosAdapter;
import com.ejemplos.spring.model.Usuario;
import com.ejemplos.spring.repository.UsuarioRepository;
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
	
	@Autowired
	UsuarioRepository usuarioRepository;

	/**
     * Buscar un usuario por correo electrónico.
     * 
     * @param email Correo electrónico del usuario que se desea buscar.
     * @return ResponseEntity con el usuario encontrado, o un mensaje de error si no se encuentra en la base de datos.
     */
    @Operation(
        summary = "Buscar un usuario por correo", 
        description = "Permite encontrar un usuario específico en la base de datos utilizando su dirección de correo electrónico."
    )
	@GetMapping("/{email}")
	public ResponseEntity<?> findByEmail(@PathVariable String email) {

		Optional<Usuario> result = usuarioService.findByEmail(email);

		if (!result.isPresent())
		{
			Map<String, Object> response = new HashMap<>();
			response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("timestamp", System.currentTimeMillis());
			response.put("status", HttpStatus.NOT_FOUND);
			response.put("message",
					"No se ha encontrado ningun usuario con el correo: "+ email);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.status(HttpStatus.OK).body(usuarioAdapter.of(result.get()));

	}

    /**
     * Crear un nuevo usuario en la base de datos.
     * 
     * @param usuarioResponse Objeto con los datos del usuario que se desea crear. 
     *                        El Id_usuario será ignorado si se especifica en la entrada JSON.
     * @return ResponseEntity con el usuario creado, o un mensaje de error si el correo ya existe o no se puede guardar.
     */
    @Operation(
        summary = "Dar de alta un nuevo usuario", 
        description = "Permite crear un nuevo usuario en la base de datos. Ignora el Id_usuario si se especifica en el Json de entrada."
    )
	@PostMapping()
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioResponse usuarioResponse) {

		// Se ignora el id si se envía
		if (usuarioResponse.getId_usuario() != null) {
			usuarioResponse.setId_usuario(null);
		}
		
		Optional<Usuario> usuarioRepetido = usuarioRepository.findByEmail(usuarioResponse.getEmail());
		
		if (usuarioRepetido.isPresent()) {
			Map<String, Object> response = new HashMap<>();
			response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("timestamp", System.currentTimeMillis());
			response.put("status", HttpStatus.BAD_REQUEST);
			response.put("message",
					"El mail del usuario debe ser único.");
			response.put("input", usuarioResponse);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		// Se guarda el Usuario covertido a Entidad
		Optional<Usuario> result =  usuarioService.saveUsuario(usuarioAdapter.of(usuarioResponse));
		
		// Validar si el evento fue guardado
			if (!result.isPresent()) {
				Map<String, Object> response = new HashMap<>();
				response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
				response.put("timestamp", System.currentTimeMillis());
				response.put("status", HttpStatus.BAD_REQUEST);
				response.put("message",
						"No se pudo guardar el usuario en la base de datos");
				response.put("input", usuarioResponse);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

		// Se construye la ResponseEntity con el código 201
		return ResponseEntity.status(HttpStatus.CREATED).body(result);

	}

}
