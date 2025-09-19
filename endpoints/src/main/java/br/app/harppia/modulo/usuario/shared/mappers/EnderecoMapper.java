package br.app.harppia.modulo.usuario.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.app.harppia.modulo.usuario.cadastro.dto.EnderecoCadastroDTO;
import br.app.harppia.modulo.usuario.shared.entity.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

	EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)	
	Endereco toEntity(EnderecoCadastroDTO endCadDTO);
}
