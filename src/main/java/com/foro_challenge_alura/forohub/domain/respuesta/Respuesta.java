package com.foro_challenge_alura.forohub.domain.respuesta;

import com.foro_challenge_alura.forohub.domain.respuesta.dto.ActualizarRespuestaDTO;
import com.foro_challenge_alura.forohub.domain.respuesta.dto.CrearRespuestaDTO;
import com.foro_challenge_alura.forohub.domain.topico.Topico;
import com.foro_challenge_alura.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "respuestas")
@Entity(name = "Respuesta")
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    private Boolean solucion;
    private Boolean borrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario  usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    // Constructor para crear respuesta
    public Respuesta(CrearRespuestaDTO crearRespuestaDTO, Usuario usuario, Topico topico) {
        this.mensaje = crearRespuestaDTO.mensaje();
        this.fechaCreacion = LocalDateTime.now();  // Se asigna la fecha actual de creación
        this.ultimaActualizacion = LocalDateTime.now(); // Se asigna la fecha actual para la última actualización
        this.solucion = false;
        this.borrado = false; // Se establece el valor por defecto a false (no borrado)
        this.usuario = usuario;
        this.topico = topico;
    }

    // Método para actualizar respuesta
    public void actualizarRespuesta(ActualizarRespuestaDTO actualizarRespuestaDTO) {
        this.mensaje = actualizarRespuestaDTO.mensaje();
        this.solucion = actualizarRespuestaDTO.solucion();
        this.ultimaActualizacion = LocalDateTime.now(); // Actualiza la fecha de la última actualización
    }

    // Método para eliminar (borrar) la respuesta (soft delete)
    public void eliminarRespuesta() {
        this.borrado = true;  // Marcar como borrado
        this.ultimaActualizacion = LocalDateTime.now(); // Establecer la fecha de la última actualización
    }
}
