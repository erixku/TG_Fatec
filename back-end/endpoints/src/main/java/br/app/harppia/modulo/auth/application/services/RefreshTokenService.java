package br.app.harppia.modulo.auth.application.services;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.JwtServiceExcpetion;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.auth.request.InformacoesLoginUsuario;
import br.app.harppia.modulo.auth.domain.auth.request.RefreshTokenRequest;
import br.app.harppia.modulo.auth.domain.auth.response.RefreshTokenResponse;

@Service
public class RefreshTokenService {

	private final static String REDIS_KEY_PREFIX = "rt_hash:";
	private final RedisTemplate<String, String> redisTemplate;

	private final JwtService jwtService;
	private final ConsultarUsuarioPort cup;

	public RefreshTokenService(RedisTemplate<String, String> redisTemplate, JwtService jwtService,
			ConsultarUsuarioPort cup) {
		this.redisTemplate = redisTemplate;
		this.jwtService = jwtService;
		this.cup = cup;
	}

	public void salvarRefreshToken(UUID userId, String refreshToken) {
		if (userId == null || refreshToken == null || refreshToken.trim().isEmpty())
			throw new JwtServiceExcpetion("id ou refresh token inválidos!");

		String redisKey = REDIS_KEY_PREFIX.concat(HashService.hashToken(refreshToken));

		redisTemplate.opsForValue().set(redisKey, userId.toString(), 7, TimeUnit.DAYS);
	}

	public RefreshTokenResponse atualizarRefreshToken(RefreshTokenRequest request) {

		String redisKey = REDIS_KEY_PREFIX.concat(HashService.hashToken(request.rawRefreshToken()));

		String userIdFromRedis = redisTemplate.opsForValue().getAndDelete(redisKey);

		if (userIdFromRedis == null || userIdFromRedis.isEmpty())
			throw new JwtServiceExcpetion("Refresh Token inválido ou expirado.");
		
		if (!userIdFromRedis.equals(request.userId().toString())) {
	        // implementar lógica de deletar todos os tokens do userIdFromRedis
			// ...
			
	        throw new SecurityException("Violação de segurança: Tentativa de refresh de token com ID de usuário incorreto.");
	    }
		
		InformacoesLoginUsuario userInfo = cup.findById(request.userId());

		String newAccessToken = jwtService.generateAccessToken(userInfo);
		String newRefreshToken = jwtService.generateRefreshToken(userInfo);

		salvarRefreshToken(userInfo.id(), newRefreshToken);

		return new RefreshTokenResponse(userInfo.id(), newAccessToken, newRefreshToken); 
	}
}