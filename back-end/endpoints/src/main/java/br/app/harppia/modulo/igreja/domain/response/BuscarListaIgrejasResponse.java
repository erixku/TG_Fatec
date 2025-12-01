package br.app.harppia.modulo.igreja.domain.response;

import java.util.List;

import br.app.harppia.modulo.igreja.domain.valueobject.InformacaoIgrejaRVO;
import lombok.Builder;

@Builder
public record BuscarListaIgrejasResponse(
		List<InformacaoIgrejaRVO> igrejas
	) {
}
