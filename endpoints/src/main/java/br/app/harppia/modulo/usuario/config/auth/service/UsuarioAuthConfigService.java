package br.app.harppia.modulo.usuario.config.auth.service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.app.harppia.modulo.usuario.autenticacao.dto.UsuarioAutenticacaoTokenDTO;
import br.app.harppia.modulo.usuario.shared.entity.Usuario;
import br.app.harppia.modulo.usuario.shared.repository.UsuarioRepository;

@Service
public class UsuarioAuthConfigService {

	@Autowired
	private UsuarioRepository userRepo;

	public UsuarioAutenticacaoTokenDTO buscarPorLogin(String login) {

		login = Normalizer.normalize(login.trim().toLowerCase(), Normalizer.Form.NFC);

		Optional<Usuario> user = userRepo.findByCpfOrEmailOrTelefone(login, login, login);

		if (user.isEmpty())
			return null;

		UsuarioAutenticacaoTokenDTO userDTO = null;

		try {

			// Dívida técnica: implementar busca por ROLES no banco de dados
			// Lógica temporária: atribuição do "ROLE_USER" para todos.
			userDTO = new UsuarioAutenticacaoTokenDTO(user.get().getUuid(), user.get().getNome(), login,
					List.of(new SimpleGrantedAuthority("ROLE_LEVITA")));

		} catch (Exception ex) {
			System.err.println("Houve algum erro na autenticação... \n" + ex.getMessage());
		}

		return userDTO;
	}
}
