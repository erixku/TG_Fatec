package br.app.harppia.modulo.usuario.application.port.out;

import java.util.List;
import java.util.UUID;

import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;

public interface ConsultarIgrejaUserToChurchPort {
	
	public List<RolesMembroPorIgrejaMinisterioRVO> rolesMembro(UUID id);
}
