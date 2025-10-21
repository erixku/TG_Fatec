package br.app.harppia.modulo.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import br.app.harppia.modulo.file.application.service.BucketCadastroService;
import br.app.harppia.modulo.file.infrastructure.repository.entities.BucketArquivo;
import br.app.harppia.modulo.shared.entity.storage.Arquivo;
import br.app.harppia.modulo.usuario.domain.dto.register.ArquivoCadastroDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.BucketCadastroDTO;

@Mapper(componentModel = "spring")
public abstract class ArquivoMapper { // Transforme a interface em uma classe abstrata

    @Autowired
    protected BucketCadastroService bucketService; // Injete o serviço como um campo
    
    // Dados da Entidade que o DTO não tem:
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    
    @Mapping(source = "bucket", target = "bucket", qualifiedByName = "buscarBucketPorNome")
    public abstract Arquivo toEntity(ArquivoCadastroDTO dto);

    // Agora o método default usa o serviço que foi injetado na classe
    @Named("buscarBucketPorNome")
    protected BucketArquivo map(BucketCadastroDTO bucketDto) {
    	
        if (bucketDto == null || bucketDto.nome() == null) 
            return null;
         
        // Use o serviço injetado na classe
        BucketArquivo bucket = bucketService.findByNome(bucketDto.nome())
                .orElseThrow(() -> new IllegalStateException("Bucket inválido: " + bucketDto.nome()));
        
        
        return bucket;
    }
}