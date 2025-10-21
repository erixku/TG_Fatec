package br.app.harppia.modulo.auth.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.usuario.domain.dto.LoginUsuarioDTO;
import br.app.harppia.modulo.usuario.domain.entities.Usuario;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class LogarUsuarioUseCase {
	
	private UsuarioRepository loginUserRepo;

	public LogarUsuarioUseCase(UsuarioRepository loginUserRepo) {
		this.loginUserRepo = loginUserRepo;
	}

	/**
	 * Método principal na lógica de login. Verifica qual valor de entrada foi
	 * dado pelo o usuário e direciona o processador de acordo.
	 * 
	 * @param loginUserDTO - dados do usuário extraídos do JSON da requisição
	 * @return true se o login fornecido é valido ou false caso não seja.
	 */
	public boolean verificarLoginUsuario(@Valid LoginUsuarioDTO loginUserDTO) {

		if (!loginUserDTO.email().isEmpty() || loginUserDTO.email() == null)
			return loginComEmail(loginUserDTO.email(), loginUserDTO.senha());

		else if (!loginUserDTO.cpf().isEmpty() || loginUserDTO.cpf() == null)
			return loginComCpf(loginUserDTO.email(), loginUserDTO.senha());

		else if (!loginUserDTO.telefone().isEmpty() || loginUserDTO.telefone() == null)
			return loginComTelefone(loginUserDTO.email(), loginUserDTO.senha());

		else
			return false;
	}

	/**
	 * Lógica de login através do email.
	 * @param email email do usuário
	 * @param senha senha do usuário
	 * @return true se as credenciais existirem e forem válidas ou false caso não sejam.
	 */
	private boolean loginComEmail(String email, String senha) {

		List<Usuario> users = loginUserRepo.findByEmail(email);
		
		if (users.size() < 1)
			return false;

		return verificarSenha(users.getFirst().getSenha(), senha);
	}

	/**
	 * Lógica de login através do CPF.
	 * @param cpf CPF do usuário
	 * @param senha senha do usuário
	 * @return true se as credenciais existirem e forem válidas ou false caso não sejam.
	 */
	private boolean loginComCpf(String cpf, String senha) {
		List<Usuario> users = loginUserRepo.findByCpf(cpf);

		if (users.size() < 1)
			return false;

		return verificarSenha(users.getFirst().getSenha(), senha);
	}

	/**
	 * Lógica de login através do telefone.
	 * @param telefone telefone do usuário
	 * @param senha senha do usuário
	 * @return true se as credenciais existirem e forem válidas ou false caso não sejam.
	 */
	private boolean loginComTelefone(String telefone, String senha) {
		List<Usuario> users = loginUserRepo.findByTelefone(telefone);
		
		if(users.size() < 1)
			return false;
		
		return verificarSenha(users.getFirst().getSenha(), senha);
	}

	/**
	 * Verifica se a senha fornecida pelo usuário é a mesma que a registrada no banco.
	 * @param senhaArmazenada é a senha persistida e recuperada do banco de dados
	 * @param senhaInserida é a senha inserida pelo usuário no ato de login
	 * @return <i>true</i> se forem iguais ou <i>false</i> se não.
	 */
	private boolean verificarSenha(String senhaArmazenada, String senhaInserida) {
		return (senhaInserida.equals(senhaArmazenada)) ? true : false;
	}
}
