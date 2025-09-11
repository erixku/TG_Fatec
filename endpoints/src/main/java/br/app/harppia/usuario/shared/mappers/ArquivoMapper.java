package br.app.harppia.usuario.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.app.harppia.usuario.cadastro.dto.ArquivoCadastroDTO;
import br.app.harppia.usuario.shared.entity.Arquivo;

@Mapper(componentModel = "spring")
public interface ArquivoMapper {
	
	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);  
	
	// Dados da Entidade que o DTO não tem:
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	
	// Dados do `bucket` a serem ignorados:
	@Mapping(target = "bucket.id", ignore = true)
	@Mapping(target = "bucket.createdAt", ignore = true)
	@Mapping(target = "bucket.updatedAt", ignore = true)
	@Mapping(target = "bucket.deletedAt", ignore = true)
	@Mapping(target = "bucket.isDeleted", ignore = true)
	@Mapping(target = "bucket.tamanhoMaximo", ignore = true)
	@Mapping(target = "bucket.tamanhoMinimo", ignore = true)
	@Mapping(target = "bucket.tempoLimiteUpload", ignore = true)
	Arquivo toEntity(ArquivoCadastroDTO dto);
}
