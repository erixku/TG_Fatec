package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.auth.request.InformacoesAutenticacaoUsuario;

public interface ConsultarUsuarioPort {
		
	InformacoesAutenticacaoUsuario porId(UUID id);
	
	InformacoesAutenticacaoUsuario informacoesAutenticacao(String cpf, String email, String Telefone);
}
