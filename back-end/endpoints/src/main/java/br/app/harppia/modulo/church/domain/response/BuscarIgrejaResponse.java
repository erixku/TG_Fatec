package br.app.harppia.modulo.church.domain.response;

import br.app.harppia.modulo.church.domain.valueobject.InformacaoIgrejaRVO;
import lombok.Builder;

@Builder
public record BuscarIgrejaResponse(InformacaoIgrejaRVO infIgrRVO) {
}
