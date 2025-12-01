package br.app.harppia.modulo.ministry.domain.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CriarMinisterioResponse(
		UUID idMinisterio,
		String nome,
		String descricao
	) {

}
