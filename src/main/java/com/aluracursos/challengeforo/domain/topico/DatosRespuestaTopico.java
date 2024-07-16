package com.aluracursos.challengeforo.domain.topico;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        java.time.LocalDateTime fechaCreacion
) {
}
