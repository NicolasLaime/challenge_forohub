package com.foro_challenge_alura.forohub.domain.usuario.dto;

import com.foro_challenge_alura.forohub.domain.usuario.Role;

public record ActualizarUsuarioDTO(
        String password,
        Role role,
        String nombre,
        String apellido,
        String email,
        Boolean enabled
) {






}
