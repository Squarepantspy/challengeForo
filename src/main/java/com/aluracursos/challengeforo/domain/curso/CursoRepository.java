package com.aluracursos.challengeforo.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso,Long> {
    Optional<Curso> findByNombre(String nombre);

}
