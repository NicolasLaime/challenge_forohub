package com.foro_challenge_alura.forohub.domain.usuario;

import com.foro_challenge_alura.forohub.domain.usuario.dto.ActualizarUsuarioDTO;
import com.foro_challenge_alura.forohub.domain.usuario.dto.CrearUsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String nombre;
    private String apellido;
    private String email;
    private Boolean enabled;

    // Constructor que utiliza CrearUsuarioDTO y un hash de contraseña
    public Usuario(CrearUsuarioDTO crearUsuarioDTO, String hashedPassword) {
        this.username = crearUsuarioDTO.username();
        this.password = hashedPassword;
        this.role = Role.USUARIO;
        this.nombre = capitalizado(crearUsuarioDTO.nombre());
        this.apellido = capitalizado(crearUsuarioDTO.apellido());
        this.email = crearUsuarioDTO.email();
        this.enabled = true;
    }

    // Método para actualizar el usuario con posibilidad de cambiar la contraseña
    public void actualizarUsuarioConPassword(ActualizarUsuarioDTO actualizarUsuarioDTO, String hashedPassword) {
        if (actualizarUsuarioDTO.password() != null) {
            this.password = hashedPassword; // Se actualiza la contraseña si está presente
        }
        if (actualizarUsuarioDTO.role() != null) {
            this.role = actualizarUsuarioDTO.role();
        }
        if (actualizarUsuarioDTO.nombre() != null) {
            this.nombre = capitalizado(actualizarUsuarioDTO.nombre());
        }
        if (actualizarUsuarioDTO.apellido() != null) {
            this.apellido = capitalizado(actualizarUsuarioDTO.apellido());
        }
        if (actualizarUsuarioDTO.email() != null) {
            this.email = actualizarUsuarioDTO.email();
        }
        if (actualizarUsuarioDTO.enabled() != null) {
            this.enabled = actualizarUsuarioDTO.enabled();
        }
    }

    //Método para eliminar un usuario (lógica de "soft delete")
    public void eliminarUsuario() {
        this.enabled = false; // Se desactiva el usuario
    }

    //Método auxiliar para capitalizar nombres y apellidos
    private String capitalizado(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    // Implementación de métodos de UserDetails para Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Se asume que la cuenta no expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Se asume que la cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Se asume que las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return this.enabled; // Devuelve el estado del usuario
    }
}
