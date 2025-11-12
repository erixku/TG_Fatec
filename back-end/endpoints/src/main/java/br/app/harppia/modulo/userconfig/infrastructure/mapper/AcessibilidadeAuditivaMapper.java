package br.app.harppia.modulo.userconfig.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.userconfig.domain.request.AtualizarAcessibilidadeAuditivaRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeAuditivaRequest;
import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeAuditivaEntity;

@Mapper(componentModel = "spring")
public interface AcessibilidadeAuditivaMapper {

	@Mapping(target = "updatedAt", ignore = true)	
	@Mapping(target = "alertasVisuais", ignore = true)
	@Mapping(target = "intensidadeFlash", ignore = true)
	@Mapping(target = "modoFlash", ignore = true)
	@Mapping(target = "transcricaoAudio", ignore = true)
	@Mapping(target = "vibracaoAprimorada", ignore = true)
	AcessibilidadeAuditivaEntity toEntity(SalvarAcessibilidadeAuditivaRequest slvAcbAudReq);

	@Mapping(target = "updatedAt", ignore = true)	
	AcessibilidadeAuditivaEntity toEntity(AtualizarAcessibilidadeAuditivaRequest atuAcbAudReq);
}
