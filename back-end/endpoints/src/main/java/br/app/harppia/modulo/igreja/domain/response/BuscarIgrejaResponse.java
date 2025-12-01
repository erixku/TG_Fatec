package br.app.harppia.modulo.igreja.domain.response;

import br.app.harppia.modulo.igreja.domain.valueobject.InformacaoIgrejaRVO;
import lombok.Builder;

@Builder
public record BuscarIgrejaResponse(InformacaoIgrejaRVO infIgrRVO) {
}
