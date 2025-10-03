package br.app.harppia.modulo.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.app.harppia.modulo.shared.entity.storage.Bucket;
import br.app.harppia.modulo.usuario.cadastro.dto.BucketCadastroDTO;

@Mapper(componentModel = "spring")
public interface BucketMapper {
	
	BucketMapper INSTANCE = Mappers.getMapper(BucketMapper.class);
	
	// Propriedades que o DTO não possui:
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "tamanhoMaximo", ignore = true)
	@Mapping(target = "tamanhoMinimo", ignore = true)
	@Mapping(target = "tempoLimiteUpload", ignore = true)
	Bucket toEntity(BucketCadastroDTO dto);
}
