package br.app.harppia.modulo.activities.domain.response;

import java.util.List;

import br.app.harppia.modulo.activities.infrastructure.projection.DadosPublicacaoIVO;

public record BuscarPublicacoesResponse(List<DadosPublicacaoIVO> lstDadosPublicacoes) {

}
