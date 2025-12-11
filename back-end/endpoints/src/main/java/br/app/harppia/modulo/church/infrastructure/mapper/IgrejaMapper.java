package br.app.harppia.modulo.church.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.app.harppia.modulo.church.domain.request.CadastroIgrejaRequest;
import br.app.harppia.modulo.church.infrastructure.repository.entities.IgrejaEntity;
import br.app.harppia.modulo.church.infrastructure.repository.enums.EDenominacaoIgreja;

@Mapper(componentModel = "spring")
public abstract class IgrejaMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "deletedBy", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "idFoto", ignore = true)

	@Mapping(source = "idCriador", target = "createdBy")
	@Mapping(source = "idCriador", target = "updatedBy")
	@Mapping(source = "idCriador", target = "idProprietario")
	@Mapping(source = "nome", target = "nome")
	
	@Mapping(source = "denominacao", target = "denominacao", qualifiedByName = "parseDenominacao")
	@Mapping(source = "cnpj", target = "cnpj", qualifiedByName = "mapCnpj")
	public abstract IgrejaEntity toEntity(CadastroIgrejaRequest dto);
	
	@Named("parseDenominacao")
	protected EDenominacaoIgreja mapDenominacao(String strDenominacao) {
		try {
			return EDenominacaoIgreja.fromValue(strDenominacao);
		}catch(Exception e) {
			return EDenominacaoIgreja.OUTRA;
		}
	}
	
	@Named("mapCnpj")
	protected String mapCnpj(String strCnpj) {
		return strCnpj.replaceAll("\\D", "");
	}
}
