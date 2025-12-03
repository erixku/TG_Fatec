package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;

public interface ConsultarUsuarioAuthPort {
		
	InformacoesAutenticacaoUsuarioRVO porId(UUID id);
	
	UUID porEmail(String email);
	
	InformacoesAutenticacaoUsuarioRVO informacoesAutenticacao(String cpf, String email, String Telefone);
}
