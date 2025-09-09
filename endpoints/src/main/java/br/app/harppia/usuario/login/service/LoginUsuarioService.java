package br.app.harppia.usuario.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.harppia.usuario.login.dto.LoginUsuarioDTO;
import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.usuario.shared.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class LoginUsuarioService {
	
	@Autowired
	private UsuarioRepository loginUserRepo;

	/**
	 * Método principal na lógica de login. Verifica qual valor de entrada foi
	 * dado pelo o usuário e direciona o processador de acordo.
	 * 
	 * @param loginUserDTO - dados do usuário extraídos do JSON da requisição
	 * @return true se o login fornecido é valido ou false caso não seja.
	 */
	public boolean verificarLoginUsuario(@Valid LoginUsuarioDTO loginUserDTO) {

		if (!loginUserDTO.getEmail().isEmpty() || loginUserDTO.getEmail() == null)
			return loginComEmail(loginUserDTO.getEmail(), loginUserDTO.getSenha());

		else if (!loginUserDTO.getCpf().isEmpty() || loginUserDTO.getCpf() == null)
			return loginComCpf(loginUserDTO.getCpf(), loginUserDTO.getSenha());

		else if (!loginUserDTO.getTelefone().isEmpty() || loginUserDTO.getTelefone() == null)
			return loginComTelefone(loginUserDTO.getEmail(), loginUserDTO.getSenha());

		else
			return false;
	}

	private boolean loginComEmail(String email, String senha) {
		List<Usuario> users = loginUserRepo.findByEmail(email);

		if (users.size() < 1)
			return false;

		return verificarSenha(users.getFirst(), senha);
	}

	private boolean loginComCpf(String cpf, String senha) {
		List<Usuario> users = loginUserRepo.findByCpf(cpf);

		if (users.size() < 1)
			return false;

		return verificarSenha(users.getFirst(), senha);
	}

	private boolean loginComTelefone(String telefone, String senha) {
		List<Usuario> users = loginUserRepo.findByTelefone(telefone);
		
		if(users.size() < 1)
			return false;
		
		return verificarSenha(users.getFirst(), senha);
	}

	private boolean verificarSenha(Usuario user, String senha) {
		return (user.getPassword().equals(senha)) ? true : false;
	}
}
