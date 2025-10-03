package br.app.harppia.modulo.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.app.harppia.modulo.shared.entity.auth.Endereco;
import br.app.harppia.modulo.usuario.cadastro.dto.EnderecoCadastroDTO;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

	EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)	
	Endereco toEntity(EnderecoCadastroDTO endCadDTO);
}
