package br.app.harppia.modulo.auth.application.services;

import java.security.Key;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	@Value("${application.security.jwt.secret-key}")
	private String secretKey;

	@Value("${application.security.jwt.expiration}")
	private Long accessExpiration;

	@Value("${application.security.jwt.refresh-token.expiration}")
	private Long refreshExpiration;

	private final ObjectMapper objMpr;
	
	public String generateAccessToken(List<RolesMembroPorIgrejaMinisterioRVO> rolesIgreja, UserDetails userDetails) {

		HashMap<String, Object> extraClaims = new HashMap<>();

		extraClaims.put("roles", userDetails.getAuthorities());
		extraClaims.put("jti", UUID.randomUUID().toString());
		extraClaims.put("iss", "harppia-api");
		extraClaims.put("aud", "harppia-client");
		extraClaims.put("roles_igreja", rolesIgreja);

		return generateAccessToken(extraClaims, userDetails);
	}

	public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, accessExpiration);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationMillis) {
		Instant now = Instant.now();
		Instant exp = now.plusMillis(expirationMillis);

		return Jwts.builder()
				.claims(extraClaims)
				.subject(userDetails.getUsername()).
				issuedAt(Date.from(now))
				.expiration(Date.from(exp))
				.signWith(getSignInKey())
				.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		Date expiration = extractExpiration(token);
		Instant now = Instant.now().minusSeconds(5); // tolerância: 5s
		return expiration.toInstant().isBefore(now);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public List<RolesMembroPorIgrejaMinisterioRVO> extractRolesIgreja(String token) {
        return extractClaim(token, claims -> {

        	List<?> rawList = claims.get("roles_igreja", List.class);
            
            if (rawList == null) {
                return Collections.emptyList();
            }

            return rawList.stream()
                .map(obj -> objMpr.convertValue(obj, RolesMembroPorIgrejaMinisterioRVO.class))
                .toList();
        });
    }
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith((SecretKey) getSignInKey()).build().parseSignedClaims(token).getPayload();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
}