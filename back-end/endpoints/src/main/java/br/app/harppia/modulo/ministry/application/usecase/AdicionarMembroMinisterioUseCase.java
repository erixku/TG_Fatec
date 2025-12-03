package br.app.harppia.modulo.ministry.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoMinisterioException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.ministry.domain.request.AdicionarMembroRequest;
import br.app.harppia.modulo.ministry.domain.response.AdicionarMembroResponse;
import br.app.harppia.modulo.ministry.infraestructure.mapper.UsuarioFuncaoMapper;
import br.app.harppia.modulo.ministry.infraestructure.repository.MembroRepository;
import br.app.harppia.modulo.ministry.infraestructure.repository.entities.UsuarioFuncaoEntity;

@Service
public class AdicionarMembroMinisterioUseCase {

	private final MembroRepository mbmRep;
	private final UsuarioFuncaoMapper usrFncMpr;

	public AdicionarMembroMinisterioUseCase(MembroRepository mbmRep, UsuarioFuncaoMapper usrFncMpr) {
		this.mbmRep = mbmRep;
		this.usrFncMpr = usrFncMpr;
	}

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public AdicionarMembroResponse adicionarUm(AdicionarMembroRequest adcMemReq) {
		
		if(adcMemReq == null)
			throw new GestaoMinisterioException("Impossível adicionar membro: dados ausentes!");
		
		UsuarioFuncaoEntity usrFncEntMapped = usrFncMpr.toEntity(adcMemReq);
		
		UsuarioFuncaoEntity usrFncEntSaved = mbmRep.save(usrFncEntMapped);
		
		if(usrFncEntSaved.getId() == null)
			throw new GestaoMinisterioException("Houve um erro ao adicionar o membro. Tente novamente mais tarde.");
		
		AdicionarMembroResponse adcMemRes = new AdicionarMembroResponse(usrFncEntSaved.getIdLevita(), usrFncEntSaved.getFuncao());
		
		return adcMemRes;
	}

	public void adicionarVarios() {}
	
	
}
