package br.app.harppia.modulo.church.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.church.application.usecase.BuscarIgrejaUseCase;
import br.app.harppia.modulo.church.domain.response.BuscarIgrejaResponse;
import br.app.harppia.modulo.church.domain.valueobject.InformacaoIgrejaRVO;
import br.app.harppia.modulo.ministry.application.port.ConsultarIgrejaMinistryToChurchPort;

@Component
public class ConsultarIgrejaFromMinistryAdapter implements ConsultarIgrejaMinistryToChurchPort {

	private final BuscarIgrejaUseCase busIgrUC;

	public ConsultarIgrejaFromMinistryAdapter(BuscarIgrejaUseCase busIgrUC) {
		this.busIgrUC = busIgrUC;
	}

	@Override
	public InformacaoIgrejaRVO porId(UUID idIgreja) {
		BuscarIgrejaResponse bscIgrRes = busIgrUC.porId(idIgreja);

		return (bscIgrRes != null) ? bscIgrRes.infIgrRVO() : null;
	}
}
