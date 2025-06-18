package br.app.harppia.persistence.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.model.auth.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


/**
 * Essa interface é responsável por abstrair e simplificar a gestão de inserção, deleção,
 * atualização e consulta da entidade 'Usuario', bem como outros acessos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
