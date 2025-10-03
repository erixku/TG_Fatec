package br.app.harppia.modulo.shared.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.shared.entity.auth.Usuario;

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
	
	/**
	 * Método para buscar um usuário através de um ou mais identificadores. São estes: CPF, email e/ou telefone.
	 * @param cpf 
	 * @param email
	 * @param telefone
	 * @return um usuário ou null
	 */
    Optional<Usuario> findByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
}