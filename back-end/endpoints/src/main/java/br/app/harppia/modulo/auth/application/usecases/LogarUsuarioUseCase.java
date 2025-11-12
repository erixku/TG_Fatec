package br.app.harppia.modulo.auth.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.LoginUsuarioException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.application.services.AutenticarUsuarioService;
import br.app.harppia.modulo.auth.application.services.RefreshTokenService;
import br.app.harppia.modulo.auth.domain.auth.request.InformacoesAutenticacaoUsuario;
import br.app.harppia.modulo.auth.domain.login.request.LoginUsuarioRequest;
import br.app.harppia.modulo.auth.domain.login.response.LoginUsuarioResponse;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesLoginSanitizadasDTO;
import br.app.harppia.modulo.auth.infrastructure.mappers.UsuarioLoginMapper;

@Service
public class LogarUsuarioUseCase {

	private final ConsultarUsuarioPort cup;
	private final PasswordEncoder pe;
	private final UsuarioLoginMapper ulm;
	private final AutenticarUsuarioService aus;
	private final RefreshTokenService rts;

	public LogarUsuarioUseCase(ConsultarUsuarioPort conUsrPort, PasswordEncoder pwdEnc,
			UsuarioLoginMapper usrMpr, AutenticarUsuarioService autSvc,
			RefreshTokenService rfsTokSvc) {
		this.cup = conUsrPort;
		this.pe = pwdEnc;
		this.ulm = usrMpr;
		this.aus = autSvc;
		this.rts = rfsTokSvc;
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public LoginUsuarioResponse execute(LoginUsuarioRequest lgnUserDTO) {

		InformacoesLoginSanitizadasDTO infLgnSntDto = ulm.toSanitizedDto(lgnUserDTO);

		InformacoesAutenticacaoUsuario infLgnUsrBanco = cup.informacoesAutenticacao(infLgnSntDto.cpf(),
				infLgnSntDto.email(), infLgnSntDto.telefone());

		if (infLgnUsrBanco == null)
			throw new LoginUsuarioException("Usuário não encontrado!");

		if (!saoMesmaSenha(infLgnUsrBanco.senha(), infLgnSntDto.senha()))
			throw new LoginUsuarioException("Senha ou login inválidos!");

		LoginUsuarioResponse loginResponse = aus.autenticar(infLgnUsrBanco);
		
		rts.salvarRefreshToken(loginResponse.id(), loginResponse.refreshToken());		
		
		return loginResponse;
	}

	private boolean saoMesmaSenha(String senhaArmazenadaEncriptada, String senhaPuraInserida) {
		return (pe.matches(senhaPuraInserida, senhaArmazenadaEncriptada));
	}
}
