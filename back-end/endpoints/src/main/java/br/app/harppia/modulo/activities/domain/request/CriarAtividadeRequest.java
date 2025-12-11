package br.app.harppia.modulo.activities.domain.request;

import java.util.UUID;

import br.app.harppia.modulo.activities.domain.valueobject.PeriodoAtividadeRVO;
import br.app.harppia.modulo.activities.infrastructure.repository.enums.ETipoPublicacao;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record CriarAtividadeRequest(
		@NotNull UUID idCriador, 
		@NotNull UUID idIgreja,
		@Nullable UUID idMinisterio,
		@NotNull PeriodoAtividadeRVO periodo,
		@NotNull ETipoPublicacao eTipoPublicacao,
		@NotNull String eTipoAtividade,
		@NotNull String titulo, 
		@NotNull String descricao
	) {
}