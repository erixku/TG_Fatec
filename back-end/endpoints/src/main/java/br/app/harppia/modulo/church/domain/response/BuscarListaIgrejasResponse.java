package br.app.harppia.modulo.church.domain.response;

import java.util.List;

import br.app.harppia.modulo.church.domain.valueobject.InformacaoIgrejaRVO;
import lombok.Builder;

@Builder
public record BuscarListaIgrejasResponse(
		List<InformacaoIgrejaRVO> igrejas
	) {
}
