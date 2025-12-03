package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.valueobjects.IgrejasUsuarioFazParteRVO;

public interface ConsultarIgrejaAuthPort {
	
	public IgrejasUsuarioFazParteRVO vinculadasAoUsuario(UUID id);
}
