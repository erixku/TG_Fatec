package br.app.harppia.modulo.auth.domain.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
		@NotBlank UUID userId,
		@NotBlank String rawRefreshToken
	) {
}
