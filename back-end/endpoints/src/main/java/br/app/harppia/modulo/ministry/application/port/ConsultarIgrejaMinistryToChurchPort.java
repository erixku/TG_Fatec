package br.app.harppia.modulo.ministry.application.port;

import java.util.UUID;

import br.app.harppia.modulo.church.domain.valueobject.InformacaoIgrejaRVO;

public interface ConsultarIgrejaMinistryToChurchPort {

	public InformacaoIgrejaRVO porId(UUID idIgreja);
}
