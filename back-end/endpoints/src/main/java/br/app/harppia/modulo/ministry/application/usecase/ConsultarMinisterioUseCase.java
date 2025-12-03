package br.app.harppia.modulo.ministry.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioRequest;
import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioResponse;
import br.app.harppia.modulo.ministry.domain.response.ListarMinisteriosResponse;
import br.app.harppia.modulo.ministry.infraestructure.repository.MinisterioRepository;
import br.app.harppia.modulo.ministry.infraestructure.repository.projection.InformacaoMinisterioProjection;

@Service
public class ConsultarMinisterioUseCase {
	
	private final MinisterioRepository minRep;

	public ConsultarMinisterioUseCase(MinisterioRepository minRep) {
		this.minRep = minRep;
	}
	
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public BuscarMinisterioResponse porNome(BuscarMinisterioRequest busMinReq){
		InformacaoMinisterioProjection infMinRVO = minRep.findByNomeContainingIgnoreCase(busMinReq.nome());
		 
		return new BuscarMinisterioResponse(infMinRVO.getId(), infMinRVO.getNome());
	}

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public ListarMinisteriosResponse listarPorNome(BuscarMinisterioRequest busMinReq){
		List<InformacaoMinisterioProjection> infMinRVO = minRep.findAllByNomeContainingIgnoreCase(busMinReq.nome());		 
		return new ListarMinisteriosResponse(infMinRVO);
	}
}
