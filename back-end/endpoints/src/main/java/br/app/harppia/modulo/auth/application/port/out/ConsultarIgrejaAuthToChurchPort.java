package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.valueobject.IgrejasUsuarioFazParteRVO;

public interface ConsultarIgrejaAuthToChurchPort {
	
	public IgrejasUsuarioFazParteRVO vinculadasAoUsuario(UUID id);
	
}
