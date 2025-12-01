package br.app.harppia.modulo.igreja.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.igreja.application.usecase.BuscarIgrejaUseCase;
import br.app.harppia.modulo.igreja.domain.response.BuscarIgrejaResponse;
import br.app.harppia.modulo.igreja.domain.valueobject.InformacaoIgrejaRVO;
import br.app.harppia.modulo.ministry.application.port.ConsultarIgrejaMinistryPort;

@Component
public class ConsultarIgrejaMinistryAdapter implements ConsultarIgrejaMinistryPort {

	private final BuscarIgrejaUseCase busIgrUC;

	public ConsultarIgrejaMinistryAdapter(BuscarIgrejaUseCase busIgrUC) {
		this.busIgrUC = busIgrUC;
	}

	@Override
	public InformacaoIgrejaRVO porId(UUID idIgreja) {
		BuscarIgrejaResponse bscIgrRes = busIgrUC.porId(idIgreja);

		return (bscIgrRes != null) ? bscIgrRes.infIgrRVO() : null;
	}
}
