package br.app.harppia.modulo.ministry.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.ministry.domain.request.CriarMinisterioRequest;
import br.app.harppia.modulo.ministry.infraestructure.repository.entities.MinisterioEntity;

@Mapper(componentModel = "spring")
public interface MinisterioMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "deletedBy", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "codigo", ignore = true)
	@Mapping(target = "idFoto", ignore = true)
	@Mapping(target = "idIgreja", ignore = true)

	@Mapping(target = "createdBy", source = "idCriador")
	@Mapping(target = "updatedBy", source = "idCriador")
	public MinisterioEntity toEntity(CriarMinisterioRequest criMinReq);
}
