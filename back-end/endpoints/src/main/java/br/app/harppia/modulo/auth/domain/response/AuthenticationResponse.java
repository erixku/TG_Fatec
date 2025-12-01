package br.app.harppia.modulo.auth.domain.response;

import java.util.UUID;

public record AuthenticationResponse(UUID userId, String jwtToken) {
}
