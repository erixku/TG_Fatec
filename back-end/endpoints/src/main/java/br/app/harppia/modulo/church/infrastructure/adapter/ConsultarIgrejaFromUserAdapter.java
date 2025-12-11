package br.app.harppia.modulo.church.infrastructure.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.church.application.usecase.BuscarIgrejaUseCase;
import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import br.app.harppia.modulo.usuario.application.port.out.ConsultarIgrejaUserToChurchPort;

@Component
public class ConsultarIgrejaFromUserAdapter implements ConsultarIgrejaUserToChurchPort {
	
	private final BuscarIgrejaUseCase busIgrUC;
	
	public ConsultarIgrejaFromUserAdapter(BuscarIgrejaUseCase busIgrUC) {
		this.busIgrUC = busIgrUC;
	}

	@Override
	public List<RolesMembroPorIgrejaMinisterioRVO> rolesMembro(UUID id) {

		List<RolesMembroPorIgrejaMinisterioRVO> lstRolMemPorIgrMinRVOResponse = busIgrUC.listaRolesMembro(id);
		
		return lstRolMemPorIgrMinRVOResponse;
	}
}
