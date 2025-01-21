package com.foro_challenge_alura.forohub.controller;


import com.foro_challenge_alura.forohub.domain.usuario.Usuario;
import com.foro_challenge_alura.forohub.domain.usuario.dto.ActualizarUsuarioDTO;
import com.foro_challenge_alura.forohub.domain.usuario.dto.CrearUsuarioDTO;
import com.foro_challenge_alura.forohub.domain.usuario.dto.DetallesUsuarioDTO;
import com.foro_challenge_alura.forohub.domain.usuario.repository.UsuarioRepository;
import com.foro_challenge_alura.forohub.domain.usuario.validations.create.ValidarCrearUsuario;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Crear Topicos y publica respuestas")
public class UsuarioController {


    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<ValidarCrearUsuario> crearValidador;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo usuario en la base de datos")
    public ResponseEntity<DetallesUsuarioDTO> crearUsuario(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO, UriComponentsBuilder uriBuilder){
        crearValidador.forEach(v -> v.validate(crearUsuarioDTO));

        String hashedPassword = passwordEncoder.encode(crearUsuarioDTO.password());
        Usuario usuario = new Usuario(crearUsuarioDTO, hashedPassword);

        repository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new DetallesUsuarioDTO(usuario));
    }


    @GetMapping("/all")
    @Operation(summary = "Enumera todos los usuarios independientemente de su estado")
    public ResponseEntity<Page<DetallesUsuarioDTO>> leerTodosUsuarios(@PageableDefault(size = 5) Pageable pageable) {
        var pagina = repository.findAll(pageable).map(DetallesUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }



    @GetMapping
    @Operation(summary = "Lista solo usuarios habilitados")
    public ResponseEntity<Page<DetallesUsuarioDTO>> leerUsuariosActivos(@PageableDefault(size = 5) Pageable pageable) {
        var pagina = repository.findByEnabledTrue(pageable).map(DetallesUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }






    @GetMapping("/username/{username}")
    @Operation(summary = "Lee un único usuario por su nombre de usuario")
    public ResponseEntity<DetallesUsuarioDTO> leerUnUsuario(@PathVariable String username) {
        Usuario usuario = (Usuario) repository.findByUsername(username);
        var datosUsuario = new DetallesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Lee un único usuario por su ID")
    public ResponseEntity<DetallesUsuarioDTO> leerUnUsuario(@PathVariable Long id) {
        Usuario usuario = repository.getReferenceById(id);
        var datosUsuario = new DetallesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }











}
