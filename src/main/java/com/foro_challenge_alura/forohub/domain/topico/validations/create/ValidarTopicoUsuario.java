package com.foro_challenge_alura.forohub.domain.topico.validations.create;

import com.foro_challenge_alura.forohub.domain.topico.dto.CrearTopicoDTO;
import com.foro_challenge_alura.forohub.domain.usuario.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCreado{

     @Autowired
    private UsuarioRepository repository;

     @Override
    public void validate(CrearTopicoDTO data){
         var existeUsuario = repository.existsById(data.usuarioId());
         if(!existeUsuario){
             throw new ValidationException("Este usuario no existe");
         }

         var usuarioHabilitado = repository.findById(data.usuarioId()).get().getEnabled();
         if(!usuarioHabilitado){
             throw new ValidationException("Este usuario no esta habilitado");
         }
     }


}
