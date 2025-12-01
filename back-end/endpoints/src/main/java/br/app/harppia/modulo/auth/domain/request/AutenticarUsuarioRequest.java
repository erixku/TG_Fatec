package br.app.harppia.modulo.auth.domain.request;

import java.util.UUID;

public record AutenticarUsuarioRequest(UUID idUsuario, String email, String accessToken, String refreshToken) {
}
