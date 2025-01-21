package com.foro_challenge_alura.forohub.domain.respuesta.validations.uptdate;

import com.foro_challenge_alura.forohub.domain.respuesta.dto.ActualizarRespuestaDTO;

public interface ValidarRespuestaActualizada {

    void validate(ActualizarRespuestaDTO data, Long respuestaId);




}
