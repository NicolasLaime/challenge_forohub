package com.foro_challenge_alura.forohub.domain.topico.validations.create;


import com.foro_challenge_alura.forohub.domain.topico.dto.CrearTopicoDTO;
import com.foro_challenge_alura.forohub.domain.topico.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCreado {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(CrearTopicoDTO data){
        var topicoDuplicado = topicoRepository.existsByTituloAndMensaje(data.titulo(), data.mensaje());
        if(topicoDuplicado){
            throw new ValidationException("Este topico ya existe .revisa /topicos/" + topicoRepository.findByTitulo(data.titulo()).getId());
        }
    }



}
