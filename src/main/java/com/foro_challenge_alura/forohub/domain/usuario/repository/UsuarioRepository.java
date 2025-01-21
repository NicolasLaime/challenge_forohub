package com.foro_challenge_alura.forohub.domain.usuario.repository;


import com.foro_challenge_alura.forohub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByUsername(String username);

    UserDetails findByEmail(String email);

    Page<Usuario> findByEnabledTrue(Pageable pageable);

    Usuario getReferenceById(Long id);

    Page<Usuario> findAll(Pageable pageable);

    Usuario getReferenceByUsername(String username);

    Boolean existsByUsername(String username);


}
