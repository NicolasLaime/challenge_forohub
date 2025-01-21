package com.foro_challenge_alura.forohub.controller;

import com.foro_challenge_alura.forohub.domain.curso.Curso;
import com.foro_challenge_alura.forohub.domain.curso.dto.ActualizarCursoDTO;
import com.foro_challenge_alura.forohub.domain.curso.dto.CrearCursoDTO;
import com.foro_challenge_alura.forohub.domain.curso.dto.DetalleCursoDTO;
import com.foro_challenge_alura.forohub.domain.curso.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso", description = "Puede pertenecer a una de todas las categorías definidas")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    // Crear un nuevo curso
    @PostMapping
    @Transactional
    @Operation(summary = "Registrar un nuevo curso en la base de datos")
    public ResponseEntity<DetalleCursoDTO> crearCurso(
            @RequestBody @Valid CrearCursoDTO crearCursoDTO,
            UriComponentsBuilder uriBuilder) {

        // Crear una nueva entidad de Curso basada en el DTO
        Curso curso = new Curso(crearCursoDTO);

        // Guardar el curso en la base de datos
        repository.save(curso);

        // Crear la URI del recurso creado
        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        // Retornar el detalle del curso creado con un status 201 (CREATED)
        return ResponseEntity.created(uri).body(new DetalleCursoDTO(curso));
    }

    // Listar todos los cursos
    @GetMapping("/all")
    @Operation(summary = "Lista todos los cursos (activos e inactivos)")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursos(Pageable pageable) {
        Page<DetalleCursoDTO> pagina = repository.findAll(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    // Listar cursos activos
    @GetMapping("/activos")
    @Operation(summary = "Lista todos los cursos activos")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursosActivos(Pageable pageable) {
        Page<DetalleCursoDTO> pagina = repository.findAllByActivoTrue(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    // Leer un solo curso por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los detalles de un curso por ID")
    public ResponseEntity<DetalleCursoDTO> leerCursoPorId(@PathVariable Long id) {
        Curso curso = repository.getReferenceById(id);
        var datosDelCurso = new DetalleCursoDTO(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getActivo()
        );
        return  ResponseEntity.ok(datosDelCurso);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el nombre, la categoría y el estado del curso")
    public ResponseEntity<DetalleCursoDTO> actualizarCurso(
            @RequestBody @Valid ActualizarCursoDTO actualizarCursoDTO,
            @PathVariable Long id) {

        // Obtener una referencia del curso por ID
        Curso curso = repository.getReferenceById(id);

        // Actualizar los campos del curso utilizando el DTO
        curso.actualizarCurso(actualizarCursoDTO);

        // Crear un DetalleCursoDTO con los datos actualizados del curso
        var datosDelCurso = new DetalleCursoDTO(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getActivo()
        );

        // Retornar la respuesta con los datos actualizados
        return ResponseEntity.ok(datosDelCurso);
    }



    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un curso de la base de datos por ID")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        // Verificar si el curso existe
        Optional<Curso> cursoOptional = repository.findById(id);

        if (cursoOptional.isPresent()) {
            // Si el curso existe, eliminarlo
            repository.delete(cursoOptional.get());
            // Retornar un status 204 (No Content) indicando que la eliminación fue exitosa
            return ResponseEntity.noContent().build();
        } else {
            // Si el curso no existe, retornar un status 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }




}

