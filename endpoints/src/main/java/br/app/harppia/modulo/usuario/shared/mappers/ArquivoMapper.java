package br.app.harppia.modulo.usuario.shared.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import br.app.harppia.modulo.usuario.cadastro.dto.ArquivoCadastroDTO;
import br.app.harppia.modulo.usuario.cadastro.dto.BucketCadastroDTO;
import br.app.harppia.modulo.usuario.cadastro.service.BucketCadastroService;
import br.app.harppia.modulo.usuario.shared.entity.Arquivo;
import br.app.harppia.modulo.usuario.shared.entity.Bucket;

@Mapper(componentModel = "spring")
public abstract class ArquivoMapper { // Transforme a interface em uma classe abstrata

    @Autowired
    protected BucketCadastroService bucketService; // Injete o serviço como um campo
    
    // Dados da Entidade que o DTO não tem:
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    
    @Mapping(source = "bucket", target = "bucket", qualifiedByName = "buscarBucketPorNome")
    public abstract Arquivo toEntity(ArquivoCadastroDTO dto);

    // Agora o método default usa o serviço que foi injetado na classe
    @Named("buscarBucketPorNome")
    protected Bucket mapBucketDtoToBucket(BucketCadastroDTO bucketDto) {
    	
    	System.out.println("ArquivoMapper (L.39): bucketDTO: " + bucketDto.nome());
    	
        if (bucketDto == null || bucketDto.nome() == null) 
            return null;
         
        // Use o serviço injetado na classe
        Bucket bucket = bucketService.findByNome(bucketDto.nome())
                .orElseThrow(() -> new IllegalStateException("Bucket inválido: " + bucketDto.nome()));
        
        System.out.println("ArquivoMapper (L.48): bucket id: " + bucket.getId());
        
        return bucket;
    }
}