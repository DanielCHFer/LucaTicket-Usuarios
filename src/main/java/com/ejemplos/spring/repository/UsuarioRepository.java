package com.ejemplos.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplos.spring.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
