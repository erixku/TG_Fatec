package br.app.harppia.modulo.church.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.church.infrastructure.repository.CategoriaAtividadeRepository;
import br.app.harppia.modulo.church.infrastructure.repository.enums.ETipoAtividade;
import br.app.harppia.modulo.church.infrastructure.repository.projection.BuscarIdCategoriaIVO;

@Service
public class CategoriaAtividadeService {

	private final CategoriaAtividadeRepository catAtvRep;
	
	public CategoriaAtividadeService(CategoriaAtividadeRepository catAtvRep) {
		this.catAtvRep = catAtvRep;
	}

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public Integer getIdCategoriaFrom(String categoria) {
		
		ETipoAtividade atividade = ETipoAtividade.fromString(categoria);
		
		Optional<BuscarIdCategoriaIVO> intIdCategoria = catAtvRep.findIdCategoriaByTipo(atividade);
		
		return (intIdCategoria.isPresent()) ? intIdCategoria.get().getId() : -1;
	}
}
