package br.app.harppia.modulo.auth.application.usecases;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoAutenticacaoException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.defaults.custom.roles.ESystemRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarIgrejaAuthToChurchPort;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.auth.application.services.AutenticarUsuarioService;
import br.app.harppia.modulo.auth.application.services.RefreshTokenService;
import br.app.harppia.modulo.auth.domain.request.LoginUsuarioRequest;
import br.app.harppia.modulo.auth.domain.response.LoginUsuarioResponse;
import br.app.harppia.modulo.auth.domain.response.RefreshTokenResponse;
import br.app.harppia.modulo.auth.domain.valueobject.IgrejasUsuarioFazParteRVO;
import br.app.harppia.modulo.auth.domain.valueobject.InformacaoUsuarioLoginRVO;
import br.app.harppia.modulo.auth.domain.valueobject.InformacoesAutenticacaoUsuarioRVO;
import br.app.harppia.modulo.auth.domain.valueobject.InformacoesLoginSanitizadasRVO;
import br.app.harppia.modulo.auth.infrastructure.mappers.UsuarioLoginMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogarUsuarioUseCase {

	private final ConsultarUsuarioAuthToUsuarioPort conUsrAuthToUsrPort;
	private final ConsultarIgrejaAuthToChurchPort conIgrAuthPort;
	private final PasswordEncoder pwdEnc;
	private final UsuarioLoginMapper usrLogMpr;
	private final AutenticarUsuarioService autUsrSvc;
	private final RefreshTokenService rfsTokSvc;

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public LoginUsuarioResponse loginPadrao(LoginUsuarioRequest logUsrReq) {

		InformacoesLoginSanitizadasRVO infLgnSntDto = usrLogMpr.mapRequest(logUsrReq);

		InformacoesAutenticacaoUsuarioRVO infAutUsrBanco = conUsrAuthToUsrPort.informacoesAutenticacao(
				infLgnSntDto.cpf(), infLgnSntDto.email(), infLgnSntDto.telefone());

		if (infAutUsrBanco == null)
			throw new GestaoAutenticacaoException("Usuário não encontrado!");

		if (!saoMesmaSenha(infAutUsrBanco.getSenha(), infLgnSntDto.senha()))
			throw new GestaoAutenticacaoException("Senha ou login inválidos!");

		infAutUsrBanco.setSystemRoles(List.of(new SimpleGrantedAuthority(ESystemRoles.USUARIO.toString())));

		RefreshTokenResponse rfsTknRes = autUsrSvc.autenticar(infAutUsrBanco);
		
		rfsTokSvc.salvarRefreshToken(infAutUsrBanco.getId(), rfsTknRes.refreshToken());		
		
		InformacaoUsuarioLoginRVO infUsrLgnRVO = new InformacaoUsuarioLoginRVO(infAutUsrBanco.getId(), infAutUsrBanco.getLogin());
		IgrejasUsuarioFazParteRVO igrUsrFazPrt = conIgrAuthPort.vinculadasAoUsuario(infAutUsrBanco.getId()); 
		
		return LoginUsuarioResponse.builder()
				.infUsrLogRVO(infUsrLgnRVO)
				.igrUsrFazPrtRVO(igrUsrFazPrt)
				.systemRoles( infAutUsrBanco.getSystemRoles() )
				.churchRoles(infAutUsrBanco.getChurchRoles())
				.accessToken(rfsTknRes.accessToken())
				.refreshToken(rfsTknRes.refreshToken())
				.build();
	}
	
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public LoginUsuarioResponse aposValidarEmail(String email, String senha) {
		InformacoesAutenticacaoUsuarioRVO infAutUsrBanco = conUsrAuthToUsrPort.porEmail(email);
		
		if (infAutUsrBanco == null)
			throw new GestaoAutenticacaoException("Usuário não encontrado!");

		if (!saoMesmaSenha(infAutUsrBanco.getSenha(), senha))
			throw new GestaoAutenticacaoException("Senha ou login inválidos!");
		
		RefreshTokenResponse rfsTknRes = autUsrSvc.autenticar(infAutUsrBanco);
		
		rfsTokSvc.salvarRefreshToken(infAutUsrBanco.getId(), rfsTknRes.refreshToken());		
		
		InformacaoUsuarioLoginRVO infUsrLgnRVO = new InformacaoUsuarioLoginRVO(infAutUsrBanco.getId(), infAutUsrBanco.getLogin());
		IgrejasUsuarioFazParteRVO igrUsrFazPrt = conIgrAuthPort.vinculadasAoUsuario(infAutUsrBanco.getId()); 
		
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
