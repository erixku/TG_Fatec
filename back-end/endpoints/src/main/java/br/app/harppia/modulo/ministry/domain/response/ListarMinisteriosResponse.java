package br.app.harppia.modulo.ministry.domain.response;

import java.util.List;

import br.app.harppia.modulo.ministry.domain.valueobject.InformacaoMinisterioRVO;

public record ListarMinisteriosResponse(List<InformacaoMinisterioRVO> listInfMinRVO) {

}
