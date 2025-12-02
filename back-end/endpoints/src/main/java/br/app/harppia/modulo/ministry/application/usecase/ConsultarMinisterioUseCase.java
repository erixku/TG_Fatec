package br.app.harppia.modulo.ministry.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioRequest;
import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioResponse;
import br.app.harppia.modulo.ministry.domain.response.ListarMinisteriosResponse;
import br.app.harppia.modulo.ministry.domain.valueobject.InformacaoMinisterioRVO;
import br.app.harppia.modulo.ministry.infraestructure.repository.MinisterioRepository;

@Service
public class ConsultarMinisterioUseCase {
	
	private final MinisterioRepository minRep;

	public ConsultarMinisterioUseCase(MinisterioRepository minRep) {
		this.minRep = minRep;
	}
	
	public BuscarMinisterioResponse porNome(BuscarMinisterioRequest busMinReq){
		InformacaoMinisterioRVO infMinRVO = minRep.findByNameContainingIgnoreCase(busMinReq.nome());
		 
		return new BuscarMinisterioResponse(infMinRVO.idMinisterio(), infMinRVO.nome());
	}

	public ListarMinisteriosResponse listarPorNome(BuscarMinisterioRequest busMinReq){
//		List<InformacaoMinisterioRVO> infMinRVO = minRep.findMinisteriosByNameContainingIgnoreCase(busMinReq.nome());
		List<InformacaoMinisterioRVO> infMinRVO = minRep.findAllIds();
		 
		return new ListarMinisteriosResponse(infMinRVO);
	}
}
