package com.aluracursos.challengeforo.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        @NotBlank
        String mensaje,
        @NotBlank
        String titulo
) {
}
