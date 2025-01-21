package com.foro_challenge_alura.forohub.controller;


import com.foro_challenge_alura.forohub.domain.curso.Curso;
import com.foro_challenge_alura.forohub.domain.curso.repository.CursoRepository;
import com.foro_challenge_alura.forohub.domain.respuesta.Respuesta;
import com.foro_challenge_alura.forohub.domain.respuesta.dto.DetalleRespuestaDTO;
import com.foro_challenge_alura.forohub.domain.respuesta.repository.RespuestaRepository;
import com.foro_challenge_alura.forohub.domain.topico.Estado;
import com.foro_challenge_alura.forohub.domain.topico.Topico;
import com.foro_challenge_alura.forohub.domain.topico.dto.ActualizarTopicoDTO;
import com.foro_challenge_alura.forohub.domain.topico.dto.CrearTopicoDTO;
import com.foro_challenge_alura.forohub.domain.topico.dto.DetalleTopicoDTO;
import com.foro_challenge_alura.forohub.domain.topico.repository.TopicoRepository;
import com.foro_challenge_alura.forohub.domain.topico.validations.create.ValidarTopicoCreado;
import com.foro_challenge_alura.forohub.domain.topico.validations.uptdate.ValidarTopicoActualizado;
import com.foro_challenge_alura.forohub.domain.usuario.Usuario;
import com.foro_challenge_alura.forohub.domain.usuario.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topic", description = "Esta vinculado a un curso y usuario especificos de la app")
public class TopicoController {


    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    RespuestaRepository respuestaRepository;

    @Autowired
    List<ValidarTopicoCreado> crearValidadores;

    @Autowired
    List<ValidarTopicoActualizado> actualizarValidadores;


    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo topico en la Base de datos")
    public ResponseEntity<DetalleTopicoDTO> crearTopico(@RequestBody @Valid CrearTopicoDTO crearTopicoDTO, UriComponentsBuilder uriBuilder){
        crearValidadores.forEach(v -> v.validate(crearTopicoDTO));

        Usuario usuario = usuarioRepository.findById(crearTopicoDTO.usuarioId()).get();
        Curso curso = cursoRepository.findById(crearTopicoDTO.cursoId()).get();
        Topico topico = new Topico(crearTopicoDTO, usuario, curso);

        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleTopicoDTO(topico));
    }


    @GetMapping("/all")
    @Operation(summary = "Lee todos los temas independientes de su estado")
    public ResponseEntity<Page<DetalleTopicoDTO>> LeerTodosLosTopicos(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        var pagina = topicoRepository.findAll(pageable).map(DetalleTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }



    @GetMapping
    @Operation(summary = "Lista de temas abiertos y cerrados")
    public ResponseEntity<Page<DetalleTopicoDTO>> leerTopicosNoEliminados(
            @PageableDefault(size = 10) Pageable pageable) {
        var pagina = topicoRepository.findAllByEstadoIsNot(Estado.DELETED, pageable)
                .map(DetalleTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lee un único tema por su ID")
    public ResponseEntity<DetalleTopicoDTO> leerUnTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DetalleTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getName(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(datosTopico);
    }

    @GetMapping("/{id}/solucion")
    @Operation(summary = "Lee la respuesta del tópico marcada como su solución")
    public ResponseEntity<DetalleRespuestaDTO> leerSolucionTopico(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceByTopicoId(id);

        var datosRespuesta = new DetalleRespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.getSolucion(),
                respuesta.getBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );

        return ResponseEntity.ok(datosRespuesta);
    }


    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el título, el mensaje, el estado o el ID del curso de un tema")
    public ResponseEntity<DetalleTopicoDTO> actualizarTopico(@RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO, @PathVariable Long id) {
        actualizarValidadores.forEach(v -> v.validate(actualizarTopicoDTO));

        Topico topico = topicoRepository.getReferenceById(id);

        if (actualizarTopicoDTO.cursoId() != null) {
            Curso curso = cursoRepository.getReferenceById(actualizarTopicoDTO.cursoId());
            topico.actualizarTopicoConCurso(actualizarTopicoDTO, curso);
        } else {
            topico.actualizarTopico(actualizarTopicoDTO);
        }

        // Ajusta la creación del DTO para incluir todos los parámetros requeridos
        var datosTopico = new DetalleTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getName(),
                topico.getCurso().getCategoria()
        );

        return ResponseEntity.ok(datosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un topico")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminaarTopico();
        return ResponseEntity.noContent().build();
    }


}
