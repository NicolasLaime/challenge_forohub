package com.foro_challenge_alura.forohub.domain.curso.dto;

import com.foro_challenge_alura.forohub.domain.curso.Categoria;

public record ActualizarCursoDTO(
        String name,
        Categoria categoria,
        Boolean activo
) {
}
