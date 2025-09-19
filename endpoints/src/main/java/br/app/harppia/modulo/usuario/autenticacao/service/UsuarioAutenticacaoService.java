package br.app.harppia.modulo.usuario.autenticacao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.app.harppia.modulo.usuario.autenticacao.dto.UsuarioAutenticacaoTokenDTO;
import br.app.harppia.modulo.usuario.shared.entity.Usuario;
import br.app.harppia.modulo.usuario.shared.repository.UsuarioRepository;

@Service
public class UsuarioAutenticacaoService {

	@Autowired
	private UsuarioRepository userRepo;

	/**
	 * Esse método busca por um usuário através do email fornecido e, caso ele
	 * exista, o retorna. Do contrário, retorna `null`.
	 * 
	 * @param email - email vinculado ao usuário
	 * @return um objeto imutável com os dados do usuário ou null, se ele não existir.
	 */
	public UsuarioAutenticacaoTokenDTO buscarPorEmail(String email) {

		if (email == null || email.trim().isEmpty())
			return null;

		List<Usuario> users = userRepo.findByEmail(email);

		if (users.isEmpty())
			return null;

		// retorna um record imutável com os dados do usuário
		return new UsuarioAutenticacaoTokenDTO(users.getFirst().getUuid(),
				users.getFirst().getNome(), users.getFirst().getEmail(),
				
				// - - - - - DIVIDA TÉCNICA - - - - -
				// Implementar: busca por roles do usuário
				List.of(new SimpleGrantedAuthority("ROLE_LEVITA"))
			);
	}
}
