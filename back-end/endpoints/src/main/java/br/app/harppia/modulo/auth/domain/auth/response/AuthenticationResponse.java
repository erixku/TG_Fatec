package br.app.harppia.modulo.auth.domain.auth.response;

import java.util.UUID;

public record AuthenticationResponse(UUID userId, String jwtToken) {
}
