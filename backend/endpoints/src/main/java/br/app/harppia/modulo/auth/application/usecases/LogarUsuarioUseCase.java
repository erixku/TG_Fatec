package br.app.harppia.modulo.auth.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.LoginUsuarioException;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.request.InformacoesLoginUsuario;
import br.app.harppia.modulo.auth.domain.request.LoginUsuarioRequest;
import br.app.harppia.modulo.auth.domain.response.LoginUsuarioResponse;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesLoginSanitizadasDTO;
import br.app.harppia.modulo.auth.infrastructure.mappers.UsuarioLoginMapper;

@Service
public class LogarUsuarioUseCase {

	private final ConsultarUsuarioPort consultarUsuarioPort;
	private final PasswordEncoder passEncoder;
	private final UsuarioLoginMapper userMapper;

	public LogarUsuarioUseCase(ConsultarUsuarioPort consultarUsuarioPort, PasswordEncoder passEncoder,
			UsuarioLoginMapper userMapper) {
		this.consultarUsuarioPort = consultarUsuarioPort;
		this.passEncoder = passEncoder;
		this.userMapper = userMapper;
	}

	public LoginUsuarioResponse execute(LoginUsuarioRequest loginUserDTO) {

		InformacoesLoginSanitizadasDTO dtoSanitizado = userMapper.toSanitizedDto(loginUserDTO);

		InformacoesLoginUsuario userBanco = consultarUsuarioPort.findByCpfOrEmailOrTelefone(dtoSanitizado.cpf(),
				dtoSanitizado.email(), dtoSanitizado.telefone());

		if (userBanco == null)
			throw new LoginUsuarioException("Usuário não encontrado!");

		if (!saoMesmaSenha(userBanco.senha(), dtoSanitizado.senha()))
			throw new LoginUsuarioException("Senha ou login inválidos!");

		return new LoginUsuarioResponse(userBanco.uuid(), userBanco.login(), userBanco.nome());
	}

	private boolean saoMesmaSenha(String senhaArmazenadaEncriptada, String senhaPuraInserida) {
		return (passEncoder.matches(senhaPuraInserida, senhaArmazenadaEncriptada));
	}
}
