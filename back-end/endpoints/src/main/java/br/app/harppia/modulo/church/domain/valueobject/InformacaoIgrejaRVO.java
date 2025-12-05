package br.app.harppia.modulo.church.domain.valueobject;

import java.util.UUID;

import br.app.harppia.modulo.church.infrastructure.repository.enums.EDenominacaoIgreja;
import lombok.Builder;

@Builder
public record InformacaoIgrejaRVO(
		UUID id,
		String cnpj,
		String nome,
		EDenominacaoIgreja denominacao,
		String outraDenominacao
	) {
}
