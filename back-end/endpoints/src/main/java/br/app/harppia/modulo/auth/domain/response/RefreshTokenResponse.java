package br.app.harppia.modulo.auth.domain.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record RefreshTokenResponse(UUID userId, String accessToken, String refreshToken) {

}
