package com.foro_challenge_alura.forohub.domain.topico.validations.update;

import com.foro_challenge_alura.forohub.domain.curso.repository.CursoRepository;
import com.foro_challenge_alura.forohub.domain.topico.dto.ActualizarTopicoDTO;
import com.foro_challenge_alura.forohub.domain.topico.validations.uptdate.ValidarTopicoActualizado;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoActualizado implements ValidarTopicoActualizado {

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(ActualizarTopicoDTO data) {
        if (data.cursoId() != null) {
            var existeCurso = repository.existsById(data.cursoId());
            if (!existeCurso) {
                throw new ValidationException("Este curso no existe");
            }

            var cursoHabilitado = repository.findById(data.cursoId())
                    .orElseThrow(() -> new ValidationException("Este curso no está disponible"));
        }
    }
}