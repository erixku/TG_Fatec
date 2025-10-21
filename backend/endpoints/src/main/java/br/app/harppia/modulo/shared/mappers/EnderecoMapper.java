package br.app.harppia.modulo.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.app.harppia.modulo.usuario.domain.dto.register.EnderecoCadastroDTO;
import br.app.harppia.modulo.usuario.domain.entities.EnderecoUsuario;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

	EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

	@Mapping(target = "usuarioDono", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)	
	EnderecoUsuario toEntity(EnderecoCadastroDTO endCadDTO);
}
