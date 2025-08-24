package br.app.harppia.usuario.login.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.app.harppia.usuario.login.entitys.Usuario;

import java.util.List;

public interface LoginUsuarioRepository extends JpaRepository<Usuario, UUID>{
	List<Usuario> findByNome(String nome);
	List<Usuario> findByCpf(String cpf);
	List<Usuario> findByEmail(String email);
	List<Usuario> findByTelefone(String telefone);
}
