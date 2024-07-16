package com.aluracursos.challengeforo.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Override
    Optional<Usuario> findById(Long aLong);
}
