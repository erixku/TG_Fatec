package br.app.harppia.usuario.shared.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.usuario.shared.repository.UsuarioRepository;

@Service
public class UsuarioServiceGenerico {
	
	@Autowired
	private UsuarioRepository userRepo;
	
	/**
	 * Esse método busca por um usuário através do email fornecido e,
	 * caso ele exista, o retorna. Do contrário, retorna `null`.
	 * @param email - email vinculado ao usuário
	 * @return um objeto Usuario ou null, se ele não existir.
	 */
	public Usuario buscarPorEmail(String email){
		
		if(email == null || email.trim().isEmpty())
			return null;
		
		List<Usuario> users = userRepo.findByEmail(email); 
		
		
		// Reduzir quantidade de informações retornadas pelo método de busca!!!
		// Implementar: UsuarioAutenticacaoTokenDTO (apenas: uuid, nome, email e senha)
		// ^^^^^^^^^ - otimização!
		
		
		if(users.isEmpty())
			return null;
		
		return users.getFirst();
	}
}
