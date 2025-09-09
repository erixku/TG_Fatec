package br.app.harppia.usuario.shared.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.usuario.shared.entity.Usuario;


/**
 * Essa interface é responsável por abstrair e simplificar a gestão de inserção, deleção,
 * atualização e consulta da entidade 'Usuario', bem como outros acessos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	public List<Usuario> findByNome(String nome);
	public List<Usuario> findByEmail(String email);
	public List<Usuario> findByCpf(String cpf);
	public List<Usuario> findByTelefone(String telefone);
}
