package br.app.harppia.modulo.activities.infrastructure.mapper;

import java.time.OffsetDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.app.harppia.modulo.activities.domain.valueobject.PeriodoAtividadeRVO;
import br.app.harppia.modulo.activities.infrastructure.repository.entities.AtividadeEntity;
import io.hypersistence.utils.hibernate.type.range.Range;

@Mapper(componentModel = "spring")
public abstract class AtividadeMapper {

	@Mapping(target = "id", ignore = true)
	
	@Mapping(target = "idPublicacao", source = "idPublicacao") 
    @Mapping(target = "idCategoria", source = "intIdCategoria")
	@Mapping(target = "periodo", source = "periodo", qualifiedByName = "converterPeriodo")
	public abstract AtividadeEntity toEntity(PeriodoAtividadeRVO periodo, Long idPublicacao, Integer intIdCategoria);
	
	@Named("converterPeriodo")
	protected String mapPeriodo(PeriodoAtividadeRVO periodo) {
		
		if(periodo == null)
			return null;
		
		OffsetDateTime inicio = OffsetDateTime.parse(periodo.inicio());
	    OffsetDateTime fim = OffsetDateTime.parse(periodo.fim());
	
	    return Range.closedOpen(inicio, fim).asString();
	}
}
