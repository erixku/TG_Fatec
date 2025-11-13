package br.app.harppia.modulo.igreja.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.igreja.domain.request.CadastroEnderecoIgrejaRequest;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.EnderecoIgrejaEntity;

@Mapper(componentModel = "spring")
public interface EnderecoIgrejaMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "deletedBy", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "igreja", ignore = true)

	@Mapping(source = "isEnderecoPrincipal", target = "isPrincipal")
	EnderecoIgrejaEntity toEntity(CadastroEnderecoIgrejaRequest cadEndIgrReq);
}
