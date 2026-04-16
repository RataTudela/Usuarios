package com.example.usuarios.repository;

import com.example.usuarios.model.Usuario;
import com.google.common.base.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.*;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        Optional<Usuario> findByEmail(String email);
}
