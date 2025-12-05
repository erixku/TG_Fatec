package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.app.harppia.modulo.ministry.application.port.ConsultarMembroMinstryToUsuarioPort;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;

@Component
public class ConsultarMembroFromMinistryAdapter implements ConsultarMembroMinstryToUsuarioPort {

	private final ConsultarUsuarioUseCase conUsrUC;

	private final List<SimpleGrantedAuthority> DEFAULT_ROLES;

	public ConsultarMembroFromMinistryAdapter(ConsultarUsuarioUseCase conUsrUC) {
		this.conUsrUC = conUsrUC;

		DEFAULT_ROLES = List.of(new SimpleGrantedAuthority("LIDER"));
	}

	@Override
	public UUID idPorEmail(String email) {
		if(email == null)
			return null;
		
		return conUsrUC.idPorEmail(email);
	}
}
