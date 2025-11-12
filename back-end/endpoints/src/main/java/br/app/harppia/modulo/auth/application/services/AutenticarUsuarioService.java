package br.app.harppia.modulo.auth.application.services;

import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.JwtServiceExcpetion;
import br.app.harppia.modulo.auth.domain.auth.request.InformacoesAutenticacaoUsuario;
import br.app.harppia.modulo.auth.domain.auth.request.RefreshTokenRequest;
import br.app.harppia.modulo.auth.domain.auth.response.RefreshTokenResponse;
import br.app.harppia.modulo.auth.domain.login.response.LoginUsuarioResponse;

@Service
public class AutenticarUsuarioService {
	
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;

	public AutenticarUsuarioService(JwtService jwtService, RefreshTokenService refreshTokenService) {
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
	}

	/**
	 * Lógica usada somente após validar as credenciais do usuário. Consumido no endpoint "/login".
	 * @param request
	 * @return
	 */
	public LoginUsuarioResponse autenticar(InformacoesAutenticacaoUsuario request) {

		String accessToken = jwtService.generateAccessToken(request);
		String refreshToken = jwtService.generateRefreshToken(request);

		refreshTokenService.salvarRefreshToken(request.id(), refreshToken);

		return LoginUsuarioResponse.builder()
				.id(request.id())
				.email(request.login())
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	public RefreshTokenResponse atualizarToken(RefreshTokenRequest request) {
		
		if(request == null || request.userId() == null || request.rawRefreshToken().trim().isEmpty())
			throw new JwtServiceExcpetion("Impossível atualizar o token: informações ausentes!");
		
		return refreshTokenService.atualizarRefreshToken(request);
	}
}
