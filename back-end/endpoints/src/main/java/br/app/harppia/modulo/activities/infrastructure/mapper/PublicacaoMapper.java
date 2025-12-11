package br.app.harppia.modulo.activities.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.activities.domain.request.CriarAtividadeRequest;
import br.app.harppia.modulo.activities.infrastructure.repository.entities.PublicacaoEntity;

@Mapper(componentModel = "spring")
public interface PublicacaoMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "deletedBy", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	
	@Mapping(source = "idCriador", target = "createdBy")
	@Mapping(source = "idCriador", target = "updatedBy")
	@Mapping(source = "eTipoPublicacao", target = "tipo")
	public PublicacaoEntity toEntity(CriarAtividadeRequest criAtvReq);
}
