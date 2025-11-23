package br.app.harppia.modulo.usuario.infrasctructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import br.app.harppia.modulo.usuario.domain.dto.register.EnderecoCadastroDTO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.EnderecoUsuarioEntity;

@Mapper(componentModel = "spring")
public abstract class EnderecoMapper {

	EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

	@Mapping(target = "usuarioDono", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)	
	
	@Mapping(source = "uf", target = "uf", qualifiedByName = "mapUf")
	abstract EnderecoUsuarioEntity toEntity(EnderecoCadastroDTO endCadDTO);
	
	
	@Named("mapUf")
	String mapUf(String uf) {
		
		
		
		return null;
	}
}
