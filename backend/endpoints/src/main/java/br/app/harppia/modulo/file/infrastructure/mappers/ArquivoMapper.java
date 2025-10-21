package br.app.harppia.modulo.file.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import br.app.harppia.modulo.file.application.usecases.BuscarBucketUseCase;
import br.app.harppia.modulo.file.domain.entities.Arquivo;
import br.app.harppia.modulo.file.domain.valueobjects.BucketIdNameRecord;
import br.app.harppia.modulo.file.infrastructure.repository.entities.ArquivoEntity;
import br.app.harppia.modulo.file.infrastructure.repository.entities.BucketEntity;
import br.app.harppia.modulo.usuario.domain.dto.register.ArquivoCadastroDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.BucketCadastroDTO;

@Mapper(componentModel = "spring")
public abstract class ArquivoMapper {

    @Autowired
    protected BuscarBucketUseCase buscarBucketUseCase;
    
    //----------------------------------------//
    // CONVERSÃO DE <<DTO>> PARA >>ENTIDADE<< //
    //----------------------------------------//

    // Dados da Entidade que o DTO não tem:
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    
    @Mapping(source = "bucket", target = "bucket", qualifiedByName = "buscarBucketPorNome")
    public abstract ArquivoEntity toEntity(ArquivoCadastroDTO dto);

    // Agora o método default usa o serviço que foi injetado na classe
    @Named("buscarBucketPorNome")
    protected BucketEntity mapBucketDtoToBucket(BucketCadastroDTO bucketDto) {
    	
        if (bucketDto == null || bucketDto.nome() == null) 
            return null;

        BucketIdNameRecord bucketInfo = buscarBucketUseCase.findByNome(bucketDto.nome());
        
        BucketEntity bucket = new BucketEntity();
        bucket.setId(bucketInfo.id());
        bucket.setNome(bucketInfo.nome());
        
        return bucket;
    }

    
    //---------------------------------------//
    // CONVERSÃO DE <<DTO>> PARA >>DOMINIO<< //
    //---------------------------------------//
    
    // Dados da Entidade que o DTO não tem:

    public abstract Arquivo toDomain(ArquivoCadastroDTO dto);

    // Agora o método default usa o serviço que foi injetado na classe
    @Named("buscarBucketPorNome")
    protected BucketEntity mapBucketDtoToBucket(BucketCadastroDTO bucketDto) {
    	
        if (bucketDto == null || bucketDto.nome() == null) 
            return null;

        BucketIdNameRecord bucketInfo = buscarBucketUseCase.findByNome(bucketDto.nome());
        
        BucketEntity bucket = new BucketEntity();
        bucket.setId(bucketInfo.id());
        bucket.setNome(bucketInfo.nome());
        
        return bucket;
    }
}