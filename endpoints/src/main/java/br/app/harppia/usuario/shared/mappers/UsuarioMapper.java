package br.app.harppia.usuario.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.usuario.cadastro.dto.UsuarioCadastroDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    // DTO -> Entidade
    @Mapping(source = "endereco", target = "endId") 
    @Mapping(source = "arquivo", target ="arquivoUUID")
    
    // Campos da classe `Usuario` a serem ignorados na conversão:
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "ultimoAcesso", ignore = true)
    @Mapping(target = "authorities", ignore = true)
 
    // Campos da classe `Arquivo` a serem ignorados:
    @Mapping(target = "arquivoUUID.uuid", ignore = true)
    @Mapping(target = "arquivoUUID.createdAt", ignore = true)
    @Mapping(target = "arquivoUUID.deletedAt", ignore = true)
    @Mapping(target = "arquivoUUID.isDeleted", ignore = true)
    
    // Campos da classe `Endereco` a serem ignorados:
    @Mapping(target = "endId.id", ignore = true)
    @Mapping(target = "endId.updatedAt", ignore = true)
    
    // Campos da classe `Bucket` a serem ignorados:
    @Mapping(target = "arquivoUUID.bucket.id", ignore = true)
    @Mapping(target = "arquivoUUID.bucket.createdAt", ignore = true)
    @Mapping(target = "arquivoUUID.bucket.updatedAt", ignore = true)
    @Mapping(target = "arquivoUUID.bucket.deletedAt", ignore = true)
    @Mapping(target = "arquivoUUID.bucket.isDeleted", ignore = true)
    @Mapping(target = "arquivoUUID.bucket.tamanhoMaximo", ignore = true)
    @Mapping(target = "arquivoUUID.bucket.tamanhoMinimo", ignore = true)
    @Mapping(target = "arquivoUUID.bucket.tempoLimiteUpload", ignore = true)
    Usuario toEntity(UsuarioCadastroDTO dto);
}
