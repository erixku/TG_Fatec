package br.app.harppia.modulo.auth.domain.auth.response;

import java.util.UUID;

public record RefreshTokenResponse(UUID userId, String accessToken, String refreshToken) {

}
