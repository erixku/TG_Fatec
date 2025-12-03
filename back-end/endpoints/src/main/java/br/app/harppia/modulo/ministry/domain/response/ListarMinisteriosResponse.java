package br.app.harppia.modulo.ministry.domain.response;

import java.util.List;

import br.app.harppia.modulo.ministry.infraestructure.repository.projection.InformacaoMinisterioProjection;

public record ListarMinisteriosResponse(List<InformacaoMinisterioProjection> listInfMinRVO) {

}
