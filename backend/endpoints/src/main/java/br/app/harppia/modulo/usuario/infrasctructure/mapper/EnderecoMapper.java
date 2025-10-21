package br.app.harppia.modulo.usuario.infrasctructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.app.harppia.modulo.usuario.domain.dto.register.EnderecoCadastroDTO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.EnderecoUsuarioEntity;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

	EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

	@Mapping(target = "usuarioDono", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)	
	EnderecoUsuarioEntity toEntity(EnderecoCadastroDTO endCadDTO);
}
