package com.foro_challenge_alura.forohub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearRespuestaDTO(
        @NotBlank String mensaje,
        @NotNull Long usuarioId,
        @NotNull Long topicoId
) {
}
