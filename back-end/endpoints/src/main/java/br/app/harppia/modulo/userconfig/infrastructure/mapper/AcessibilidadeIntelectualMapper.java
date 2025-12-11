package br.app.harppia.modulo.userconfig.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.userconfig.domain.request.AtualizarAcessibilidadeIntelectualRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeIntelectualRequest;
import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeIntelectualEntity;

@Mapper(componentModel = "spring")
public interface AcessibilidadeIntelectualMapper {

	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "feedbackImediato", ignore = true)
	@Mapping(target = "modoFoco", ignore = true)
	@Mapping(target = "tamanhoIcone", ignore = true)
	AcessibilidadeIntelectualEntity toEntity(SalvarAcessibilidadeIntelectualRequest slvAcbIntReq);
		
	@Mapping(target = "updatedAt", ignore = true)
	AcessibilidadeIntelectualEntity toEntity(AtualizarAcessibilidadeIntelectualRequest atuAcbIntReq);
}
