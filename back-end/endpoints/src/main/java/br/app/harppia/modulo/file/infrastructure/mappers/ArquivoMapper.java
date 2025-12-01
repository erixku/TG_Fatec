package br.app.harppia.modulo.file.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import br.app.harppia.modulo.file.application.service.BuscarBucketService;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoCadastroDTO;
import br.app.harppia.modulo.file.infrastructure.repository.entities.ArquivoEntity;

@Mapper(componentModel = "spring")
public abstract class ArquivoMapper {

    @Autowired
    protected BuscarBucketService buscarBucketUseCase;
    
    //----------------------------------------//
    // CONVERSÃO DE <<DTO>> PARA >>ENTIDADE<< //
    //----------------------------------------//

    // Dados da Entidade que o DTO não tem:
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "idBucket", ignore = true)
    @Mapping(target = "link", ignore = true)
    
    public abstract ArquivoEntity toEntity(ArquivoCadastroDTO dto);

}