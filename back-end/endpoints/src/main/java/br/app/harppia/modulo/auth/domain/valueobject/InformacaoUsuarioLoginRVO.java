package br.app.harppia.modulo.auth.domain.valueobject;

import java.util.UUID;

import lombok.Builder;

@Builder
public record InformacaoUsuarioLoginRVO(UUID id, String login) {
}
