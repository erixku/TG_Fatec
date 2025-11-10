package br.app.harppia.modulo.auth.domain.auth.request;

import java.util.UUID;

public record RefreshTokenRequest(UUID userId, String rawRefreshToken) {

}
