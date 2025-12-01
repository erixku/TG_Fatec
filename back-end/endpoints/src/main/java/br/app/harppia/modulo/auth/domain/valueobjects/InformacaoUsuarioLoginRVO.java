package br.app.harppia.modulo.auth.domain.valueobjects;

import java.util.UUID;

import lombok.Builder;

@Builder
public record InformacaoUsuarioLoginRVO(UUID id, String nome, String email) {
}
