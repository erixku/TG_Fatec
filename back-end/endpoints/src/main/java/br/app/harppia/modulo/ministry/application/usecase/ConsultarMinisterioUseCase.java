package br.app.harppia.modulo.ministry.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioRequest;
import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioResponse;
import br.app.harppia.modulo.ministry.domain.response.ListarMinisteriosResponse;
import br.app.harppia.modulo.ministry.domain.valueobject.InformacaoMinisterioRVO;
import br.app.harppia.modulo.ministry.infraestructure.repository.MinisterioRepository;
import br.app.harppia.modulo.ministry.infraestructure.repository.projection.InformacaoMinisterioProjection;

@Service
public class ConsultarMinisterioUseCase {
	
	private final MinisterioRepository minRep;

	public ConsultarMinisterioUseCase(MinisterioRepository minRep) {
		this.minRep = minRep;
	}
	
	public BuscarMinisterioResponse porNome(BuscarMinisterioRequest busMinReq){
		InformacaoMinisterioProjection infMinRVO = minRep.findByNomeContainingIgnoreCase(busMinReq.nome());
		 
		return new BuscarMinisterioResponse(infMinRVO.getId(), infMinRVO.getNome());
	}

	public ListarMinisteriosResponse listarPorNome(BuscarMinisterioRequest busMinReq){
		List<InformacaoMinisterioProjection> infMinRVO = minRep.findAllByNomeContainingIgnoreCase(busMinReq.nome());		 
		return new ListarMinisteriosResponse(infMinRVO);
	}
}
