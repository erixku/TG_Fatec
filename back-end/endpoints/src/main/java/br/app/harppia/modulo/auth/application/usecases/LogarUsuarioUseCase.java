package br.app.harppia.modulo.auth.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoAutenticacaoException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarIgrejaAuthPort;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthPort;
import br.app.harppia.modulo.auth.application.services.AutenticarUsuarioService;
import br.app.harppia.modulo.auth.application.services.RefreshTokenService;
import br.app.harppia.modulo.auth.domain.request.LoginUsuarioRequest;
import br.app.harppia.modulo.auth.domain.response.LoginUsuarioResponse;
import br.app.harppia.modulo.auth.domain.response.RefreshTokenResponse;
import br.app.harppia.modulo.auth.domain.valueobjects.IgrejasUsuarioFazParteRVO;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacaoUsuarioLoginRVO;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesLoginSanitizadasRVO;
import br.app.harppia.modulo.auth.infrastructure.mappers.UsuarioLoginMapper;

@Service
public class LogarUsuarioUseCase {

	private final ConsultarUsuarioAuthPort conUsrAuthPort;
	private final ConsultarIgrejaAuthPort conIgrAuthPort;
	private final PasswordEncoder pwdEnc;
	private final UsuarioLoginMapper usrLogMpr;
	private final AutenticarUsuarioService autUsrSvc;
	private final RefreshTokenService rfsTokSvc;

	public LogarUsuarioUseCase(ConsultarUsuarioAuthPort conUsrAuthPort, 
			ConsultarIgrejaAuthPort conIgrAuthPort, PasswordEncoder pwdEnc,
			UsuarioLoginMapper usrLogMpr, AutenticarUsuarioService autUsrSvc,
			RefreshTokenService rfsTokSvc) {
		this.conUsrAuthPort = conUsrAuthPort;
		this.conIgrAuthPort = conIgrAuthPort;
		this.pwdEnc = pwdEnc;
		this.usrLogMpr = usrLogMpr;
		this.autUsrSvc = autUsrSvc;
		this.rfsTokSvc = rfsTokSvc;
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public LoginUsuarioResponse proceder(LoginUsuarioRequest logUsrReq) {

		InformacoesLoginSanitizadasRVO infLgnSntDto = usrLogMpr.mapRequest(logUsrReq);

		InformacoesAutenticacaoUsuarioRVO infAutUsrBanco = conUsrAuthPort.informacoesAutenticacao(infLgnSntDto.cpf(),
				infLgnSntDto.email(), infLgnSntDto.telefone());

		if (infAutUsrBanco == null)
			throw new GestaoAutenticacaoException("Usuário não encontrado!");

		if (!saoMesmaSenha(infAutUsrBanco.senha(), infLgnSntDto.senha()))
			throw new GestaoAutenticacaoException("Senha ou login inválidos!");

		RefreshTokenResponse rfsTknRes = autUsrSvc.autenticar(infAutUsrBanco);
		
		rfsTokSvc.salvarRefreshToken(infAutUsrBanco.id(), rfsTknRes.refreshToken());		
		
		InformacaoUsuarioLoginRVO infUsrLgnRVO = new InformacaoUsuarioLoginRVO(infAutUsrBanco.id(), infAutUsrBanco.email());
		IgrejasUsuarioFazParteRVO igrUsrFazPrt = conIgrAuthPort.vinculadasAoUsuario(infAutUsrBanco.id()); 
		
		return LoginUsuarioResponse.builder()
				.infUsrLogRVO(infUsrLgnRVO)
				.igrUsrFazPrtRVO(igrUsrFazPrt)
				.accessToken(rfsTknRes.accessToken())
				.refreshToken(rfsTknRes.refreshToken())
				.build();
	}

	/**
	 * Confere se as duas senhas são a mesma, através do algoritmos usado para encriptação original.
	 * @param strSenhaEncriptada a senha recuperada do banco (já encriptada)
	 * @param strSenhaRequest a senha pura enviada pelo usuário
	 * @return se conferem, <i>true</i>, senão, <i>false</i>.
	 */
	private boolean saoMesmaSenha(String strSenhaEncriptada, String strSenhaRequest) {
		return (pwdEnc.matches(strSenhaRequest, strSenhaEncriptada));
	}
}
