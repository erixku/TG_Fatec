package br.app.harppia.modulo.ministry.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.ministry.domain.request.AdicionarMembroRequest;
import br.app.harppia.modulo.ministry.infraestructure.repository.entities.UsuarioFuncaoEntity;

@Mapper(componentModel = "spring")
public interface UsuarioFuncaoMapper {

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "deletedBy", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	
	@Mapping(source = "idCriador", target = "createdBy")
	@Mapping(source = "idMembro", target = "idLevita")
	UsuarioFuncaoEntity toEntity(AdicionarMembroRequest adcMemReq);
}
