package br.app.harppia.modulo.church.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.auth.application.port.out.ConsultarIgrejaAuthToChurchPort;
import br.app.harppia.modulo.auth.domain.valueobject.IgrejasUsuarioFazParteRVO;
import br.app.harppia.modulo.church.application.usecase.BuscarIgrejaUseCase;
import br.app.harppia.modulo.church.domain.response.BuscarListaIdsIgrejasResponse;

@Component
public class ConsultarIgrejaFromAuthAdapter implements ConsultarIgrejaAuthToChurchPort {
	
	private final BuscarIgrejaUseCase busIgrUC;
	
	public ConsultarIgrejaFromAuthAdapter(BuscarIgrejaUseCase busIgrUC) {
		this.busIgrUC = busIgrUC;
	}

	@Override
	public IgrejasUsuarioFazParteRVO vinculadasAoUsuario(UUID idIgreja) {
		 BuscarListaIdsIgrejasResponse busLstIgrRes = busIgrUC.listaIgrejasVinculadasAoUsuario(idIgreja);
		
		return (busLstIgrRes != null) ? new IgrejasUsuarioFazParteRVO(busLstIgrRes.idIgrejas()) : null;
	}
}
