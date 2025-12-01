package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.valueobjects.IgrejasUsuarioFazParteRVO;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;

public interface ConsultarIgrejaAuthPort {
		
//	public InformacoesAutenticacaoUsuarioRVO porId(UUID id);
	
	public IgrejasUsuarioFazParteRVO vinculadasAoUsuario(UUID id);
}
