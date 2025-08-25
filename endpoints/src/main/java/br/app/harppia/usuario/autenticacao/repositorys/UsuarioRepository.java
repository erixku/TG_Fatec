package br.app.harppia.usuario.autenticacao.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.app.harppia.usuario.cadastro.entities.Usuario;

/**
 * Essa interface é responsável por abstrair e simplificar a gestão de inserção, deleção,
 * atualização e consulta da entidade 'Usuario', bem como outros acessos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	public UserDetails findByEmail();
	public UserDetails findByCpf();
	public UserDetails findByTelefone();
}
