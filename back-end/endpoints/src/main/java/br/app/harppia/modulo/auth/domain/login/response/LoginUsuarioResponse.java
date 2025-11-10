package br.app.harppia.modulo.auth.domain.login.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record LoginUsuarioResponse(UUID id, String email, String nome, String accessToken, String refreshToken) {

}
