package com.foro_challenge_alura.forohub.domain.respuesta.dto;

public record ActualizarRespuestaDTO(
        String mensaje,
        Boolean solucion,
        Boolean borrado
) {
}
