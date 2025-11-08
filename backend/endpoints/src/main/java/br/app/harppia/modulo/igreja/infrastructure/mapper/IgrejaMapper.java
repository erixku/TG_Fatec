package br.app.harppia.modulo.igreja.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.app.harppia.modulo.igreja.domain.request.CadastroIgrejaRequest;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

@Mapper(componentModel = "spring")
public interface IgrejaMapper {

	
	@Mapping(target="id", ignore = true)
	@Mapping(target="createdAt", ignore = true)
	@Mapping(target="updatedAt", ignore = true)
	@Mapping(target="deletedAt", ignore = true)
	@Mapping(target="deletedBy", ignore = true)
	@Mapping(target="isDeleted", ignore = true)
	@Mapping(target="idFoto", ignore = true)
	@Mapping(target="outraDenominacao", ignore = true)

	@Mapping(source="idDono", target="createdBy")
	@Mapping(source="idDono", target="updatedBy")
	@Mapping(source="idDono", target="idProprietario")
	@Mapping(source="nomeIgreja", target="nome")
	IgrejaEntity toEntity(CadastroIgrejaRequest dto);
}
