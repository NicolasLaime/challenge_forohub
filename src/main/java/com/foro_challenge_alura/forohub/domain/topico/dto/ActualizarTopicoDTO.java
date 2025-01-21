package com.foro_challenge_alura.forohub.domain.topico.dto;

import com.foro_challenge_alura.forohub.domain.topico.Estado;

public record ActualizarTopicoDTO(
        String titulo,
        String mensaje,
        Estado estado,
        Long cursoId

) {


}
