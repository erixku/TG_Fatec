package br.app.harppia.modulo.auth.application.services;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.JwtServiceExcpetion;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.auth.request.InformacoesAutenticacaoUsuario;
import br.app.harppia.modulo.auth.domain.auth.request.RefreshTokenRequest;
import br.app.harppia.modulo.auth.domain.auth.response.RefreshTokenResponse;

@Service
public class RefreshTokenService {

	private final static String REDIS_KEY_PREFIX = "rt_hash:";
	private final RedisTemplate<String, String> rdsTmp;

	private final JwtService jwtSvc;
	private final ConsultarUsuarioPort cup;

	public RefreshTokenService(RedisTemplate<String, String> redisTemplate, JwtService jwtService,
			ConsultarUsuarioPort cup) {
		this.rdsTmp = redisTemplate;
		this.jwtSvc = jwtService;
		this.cup = cup;
	}

	public void salvarRefreshToken(UUID userId, String refreshToken) {
		if (userId == null || refreshToken == null || refreshToken.trim().isEmpty())
			throw new JwtServiceExcpetion("id ou refresh token inválidos!");

		String redisKey = REDIS_KEY_PREFIX.concat(HashService.hashToken(refreshToken));

		rdsTmp.opsForValue().set(redisKey, userId.toString(), 7, TimeUnit.DAYS);
	}

	public RefreshTokenResponse atualizarRefreshToken(RefreshTokenRequest request) {

		String redisKey = REDIS_KEY_PREFIX.concat(HashService.hashToken(request.rawRefreshToken()));

		String userIdFromRedis = rdsTmp.opsForValue().getAndDelete(redisKey);

		if (userIdFromRedis == null || userIdFromRedis.isEmpty())
			throw new JwtServiceExcpetion("Refresh Token inválido ou expirado.");

		if (!userIdFromRedis.equals(request.userId().toString())) {
			// implementar lógica de deletar todos os tokens do userIdFromRedis
			// ...

			throw new SecurityException(
					"Violação de segurança: Tentativa de refresh de token com ID de usuário incorreto.");
		}

		InformacoesAutenticacaoUsuario userInfo = cup.porId(request.userId());

		String newAccessToken = jwtSvc.generateAccessToken(userInfo);
		String newRefreshToken = jwtSvc.generateRefreshToken(userInfo);

		salvarRefreshToken(userInfo.id(), newRefreshToken);

		return new RefreshTokenResponse(userInfo.id(), newAccessToken, newRefreshToken);
	}
}