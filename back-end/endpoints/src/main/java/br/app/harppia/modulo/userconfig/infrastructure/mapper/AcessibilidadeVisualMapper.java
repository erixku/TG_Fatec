package br.app.harppia.modulo.userconfig.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.userconfig.domain.request.AtualizarAcessibilidadeVisualRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeVisualRequest;
import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeVisualEntity;

@Mapper(componentModel = "spring")
public interface AcessibilidadeVisualMapper {

	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "altoContraste", ignore = true)
	@Mapping(target = "intensidadeDaltonismo", ignore = true)
	@Mapping(target = "modoDaltonismo", ignore = true)	
	@Mapping(target = "negrito", ignore = true)	
	@Mapping(target = "removerAnimacoes", ignore = true)	
	@Mapping(target = "tamanhoTexto", ignore = true)	
	@Mapping(target = "tema", ignore = true)	
	@Mapping(target = "vibrarAoTocar", ignore = true)	
	AcessibilidadeVisualEntity toEntity(SalvarAcessibilidadeVisualRequest slvAcbVisReq);
	
	@Mapping(target = "updatedAt", ignore = true)	
	AcessibilidadeVisualEntity toEntity(AtualizarAcessibilidadeVisualRequest atuAcbAudReq);
}
