package br.app.harppia.modulo.auth.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.LoginUsuarioException;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.application.services.AutenticarUsuarioService;
import br.app.harppia.modulo.auth.application.services.RefreshTokenService;
import br.app.harppia.modulo.auth.domain.auth.request.InformacoesLoginUsuario;
import br.app.harppia.modulo.auth.domain.login.request.LoginUsuarioRequest;
import br.app.harppia.modulo.auth.domain.login.response.LoginUsuarioResponse;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesLoginSanitizadasDTO;
import br.app.harppia.modulo.auth.infrastructure.mappers.UsuarioLoginMapper;

@Service
public class LogarUsuarioUseCase {

	private final ConsultarUsuarioPort consultarUsuarioPort;
	private final PasswordEncoder passEncoder;
	private final UsuarioLoginMapper userMapper;
	private final AutenticarUsuarioService authService;
	private final RefreshTokenService rts;

	public LogarUsuarioUseCase(ConsultarUsuarioPort consultarUsuarioPort, PasswordEncoder passEncoder,
			UsuarioLoginMapper userMapper, AutenticarUsuarioService authService,
			RefreshTokenService rts) {
		this.consultarUsuarioPort = consultarUsuarioPort;
		this.passEncoder = passEncoder;
		this.userMapper = userMapper;
		this.authService = authService;
		this.rts = rts;
	}

	public LoginUsuarioResponse execute(LoginUsuarioRequest loginUserDTO) {

		InformacoesLoginSanitizadasDTO dtoSanitizado = userMapper.toSanitizedDto(loginUserDTO);

		InformacoesLoginUsuario userBanco = consultarUsuarioPort.findByCpfOrEmailOrTelefone(dtoSanitizado.cpf(),
				dtoSanitizado.email(), dtoSanitizado.telefone());

		if (userBanco == null)
			throw new LoginUsuarioException("Usuário não encontrado!");

		if (!saoMesmaSenha(userBanco.senha(), dtoSanitizado.senha()))
			throw new LoginUsuarioException("Senha ou login inválidos!");

		LoginUsuarioResponse loginResponse = authService.autenticar(userBanco);
		
		rts.salvarRefreshToken(loginResponse.id(), loginResponse.refreshToken());		
		
		return loginResponse;
	}

	private boolean saoMesmaSenha(String senhaArmazenadaEncriptada, String senhaPuraInserida) {
		return (passEncoder.matches(senhaPuraInserida, senhaArmazenadaEncriptada));
	}
}
