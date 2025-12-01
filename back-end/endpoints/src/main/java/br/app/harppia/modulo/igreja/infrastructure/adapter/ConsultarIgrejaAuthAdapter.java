package br.app.harppia.modulo.igreja.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.auth.application.port.out.ConsultarIgrejaAuthPort;
import br.app.harppia.modulo.auth.domain.valueobjects.IgrejasUsuarioFazParteRVO;
import br.app.harppia.modulo.igreja.application.usecase.BuscarIgrejaUseCase;
import br.app.harppia.modulo.igreja.domain.response.BuscarListaIdsIgrejasResponse;

@Component
public class ConsultarIgrejaAuthAdapter implements ConsultarIgrejaAuthPort {
	
	private final BuscarIgrejaUseCase busIgrUC;
	
	public ConsultarIgrejaAuthAdapter(BuscarIgrejaUseCase busIgrUC) {
		this.busIgrUC = busIgrUC;
	}

	@Override
	public IgrejasUsuarioFazParteRVO vinculadasAoUsuario(UUID idIgreja) {
		 BuscarListaIdsIgrejasResponse busLstIgrRes = busIgrUC.listaIgrejasVinculadasAoUsuario(idIgreja);
		
		return (busLstIgrRes != null) ? new IgrejasUsuarioFazParteRVO(busLstIgrRes.idIgrejas()) : null;
	}
}
