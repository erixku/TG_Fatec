package br.app.harppia.modulo.auth.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoAutenticacaoException;
import br.app.harppia.defaults.custom.exceptions.JwtServiceExcpetion;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.auth.domain.request.AutenticarUsuarioRequest;
import br.app.harppia.modulo.auth.domain.request.RefreshTokenRequest;
import br.app.harppia.modulo.auth.domain.response.RefreshTokenResponse;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;

@Service
public class AutenticarUsuarioService {

	private final ConsultarUsuarioAuthToUsuarioPort conUsrAuthToUsrPort;
	private final JwtService jwtSvc;
	private final RefreshTokenService rfsTokSvc;

	public AutenticarUsuarioService(JwtService jwtService, RefreshTokenService rfsTokSvc,
			ConsultarUsuarioAuthToUsuarioPort conUsrAuthToUsrPort) {
		this.jwtSvc = jwtService;
		this.rfsTokSvc = rfsTokSvc;
		this.conUsrAuthToUsrPort = conUsrAuthToUsrPort;
	}

	/**
	 * Lógica usada somente após validar as credenciais do usuário. Consumido no
	 * endpoint "/login".
	 * 
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public RefreshTokenResponse autenticar(InformacoesAutenticacaoUsuarioRVO request) {

		String accessToken = jwtSvc.generateAccessToken(request);
		String refreshToken = jwtSvc.generateRefreshToken(request);

		rfsTokSvc.salvarRefreshToken(request.id(), refreshToken);

		return RefreshTokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

	/**
	 * Usado para validar as credenciais do usuário ao navegar pelo aplicativo.
	 * 
	 * @param autUsrReq os dados prévios da sessão do usuário (tokens)
	 * @return um novo par de tokens, se o usuário for válido
	 */
	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public RefreshTokenResponse autenticar(AutenticarUsuarioRequest autUsrReq) {

		if (autUsrReq == null || autUsrReq.email() == null)
			throw new GestaoAutenticacaoException("Dados para autenticação ausentes!");

		InformacoesAutenticacaoUsuarioRVO infLogUsrRVO = conUsrAuthToUsrPort.informacoesAutenticacao("", autUsrReq.email(),
				"");

		if (!jwtSvc.isTokenValid(autUsrReq.refreshToken(), infLogUsrRVO))
			throw new GestaoAutenticacaoException("Refresh token expirado. Por favor, faça login novamente!");

		RefreshTokenResponse rfsTknResAUpdated = rfsTokSvc
				.atualizarRefreshToken(new RefreshTokenRequest(autUsrReq.idUsuario(), autUsrReq.refreshToken()));

		return new RefreshTokenResponse(infLogUsrRVO.id(), rfsTknResAUpdated.accessToken(),
				rfsTknResAUpdated.refreshToken());
	}

	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public RefreshTokenResponse atualizarToken(RefreshTokenRequest request) {

		if (request == null || request.userId() == null || request.rawRefreshToken().trim().isEmpty())
			throw new JwtServiceExcpetion("Impossível atualizar o token: informações ausentes!");

		return rfsTokSvc.atualizarRefreshToken(request);
	}
}
