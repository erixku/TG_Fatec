package br.app.harppia.modulo.auth.application.usecases;

import java.text.Normalizer;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.request.InformacoesLoginUsuario;

@Service
public class AutenticarUsuarioUseCase {

	private ConsultarUsuarioPort consultarUsuarioPort;

	public AutenticarUsuarioUseCase(ConsultarUsuarioPort consultarUsuarioPort) {
		this.consultarUsuarioPort = consultarUsuarioPort;
	}

	public InformacoesLoginUsuario buscarPorLogin(String login) {

		login = Normalizer.normalize(login.trim().toLowerCase(), Normalizer.Form.NFC);

		InformacoesLoginUsuario user = consultarUsuarioPort.findByCpfOrEmailOrTelefone(login, login,
				login);

		if (user == null)
			return null;

		InformacoesLoginUsuario userTokenDTO = null;

		try {
			userTokenDTO = new InformacoesLoginUsuario(user.uuid(), user.nome(), login, user.senha(), user.roles());

		} catch (Exception ex) {
			System.err.println("Houve algum erro na autenticação... \n" + ex.getMessage());
		}

		return userTokenDTO;
	}
}
