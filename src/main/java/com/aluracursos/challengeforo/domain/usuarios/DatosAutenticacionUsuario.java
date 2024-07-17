package com.aluracursos.challengeforo.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @NotBlank
                @Email
        String email,
        @NotBlank
        String contrasena
) {
}