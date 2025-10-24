package br.app.harppia.modulo.auth.application.usecases;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.request.InformacoesLoginUsuario;
import br.app.harppia.modulo.auth.domain.request.LoginUsuarioDTO;
import jakarta.validation.Valid;

@Service
public class LogarUsuarioUseCase {

	private ConsultarUsuarioPort consultarUsuarioPort;

	public LogarUsuarioUseCase(ConsultarUsuarioPort consultarUsuarioPort) {
		this.consultarUsuarioPort = consultarUsuarioPort;
	}

	public boolean verificarLoginUsuario(@Valid LoginUsuarioDTO loginUserDTO) {
		InformacoesLoginUsuario user = consultarUsuarioPort.findByCpfOrEmailOrTelefone(loginUserDTO.cpf(),
				loginUserDTO.email(), loginUserDTO.telefone());

		if (user == null) return false;

		return verificarSenha(user.senha(), loginUserDTO.senha());
	}

	private boolean verificarSenha(String senhaArmazenada, String senhaInserida) {
		return ( senhaInserida.equals(senhaArmazenada) ) ? true : false;
	}
}
