package br.app.harppia.modulo.ministry.application.port;

import java.util.UUID;

import br.app.harppia.modulo.igreja.domain.valueobject.InformacaoIgrejaRVO;

public interface ConsultarIgrejaMinistryPort {

	public InformacaoIgrejaRVO porId(UUID idIgreja);
}
