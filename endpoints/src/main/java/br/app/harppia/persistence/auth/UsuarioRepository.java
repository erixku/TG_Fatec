package br.app.harppia.persistence.auth;

import org.springframework.stereotype.Repository;

import br.app.harppia.model.auth.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UsuarioRepository {

	@PersistenceContext
	private EntityManager em;

	public int cadastrarUsuario(Usuario usuario) {
		em.persist(usuario);
		return 1;
	}
}
