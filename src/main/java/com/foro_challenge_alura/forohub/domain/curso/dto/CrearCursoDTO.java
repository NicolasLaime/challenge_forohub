package com.foro_challenge_alura.forohub.domain.curso.dto;

import com.foro_challenge_alura.forohub.domain.curso.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCursoDTO(
        @NotBlank String name,
        @NotNull Categoria categoria){



}
