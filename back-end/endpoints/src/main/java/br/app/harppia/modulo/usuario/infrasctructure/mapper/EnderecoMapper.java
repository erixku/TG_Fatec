package br.app.harppia.modulo.usuario.infrasctructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.app.harppia.modulo.usuario.application.services.ConverterUFService;
import br.app.harppia.modulo.usuario.domain.dto.register.EnderecoCadastroDTO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.EnderecoUsuarioEntity;

@Mapper(componentModel = "spring")
public abstract class EnderecoMapper {

	@Mapping(target = "usuarioDono", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)

	@Mapping(source = "uf", target = "uf", qualifiedByName = "mapUf")
	@Mapping(source = "cep", target = "cep", qualifiedByName = "mapCep")
	abstract EnderecoUsuarioEntity toEntity(EnderecoCadastroDTO endCadDTO);

	@Named("mapUf")
	String mapUf(String uf) {

		if (uf == null || uf.trim().isEmpty() || (uf.length() < 2))
			return null;

		return (uf.length() != 2) ? ConverterUFService.paraSigla(uf) : uf.toUpperCase();
	}
	
	@Named("mapCep")
	String mapCep(String cep) {

		if (cep == null || cep.trim().isEmpty() || (cep.length() < 2))
			return null;

		return cep.trim().replaceAll("\\D", "");
	}
}
