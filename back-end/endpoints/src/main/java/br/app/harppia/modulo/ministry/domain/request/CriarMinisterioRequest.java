package br.app.harppia.modulo.ministry.domain.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CriarMinisterioRequest(
		@NotBlank UUID idCriador, 
		@NotBlank UUID idIgreja, 
		@NotBlank String nome,
		@NotBlank String descricao
	) {
}
