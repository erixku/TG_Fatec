package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.ministry.application.port.ConsultarMembroMinstryToUsuarioPort;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;

@Component
public class ConsultarMembroFromMinistryAdapter implements ConsultarMembroMinstryToUsuarioPort {

	private final ConsultarUsuarioUseCase conUsrUC;

	public ConsultarMembroFromMinistryAdapter(ConsultarUsuarioUseCase conUsrUC) {
		this.conUsrUC = conUsrUC;
	}

	@Override
	public UUID idPorEmail(String email) {
		if(email == null)
			return null;
		
		return conUsrUC.idPorEmail(email);
	}
}
