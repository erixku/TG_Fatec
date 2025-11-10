package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.auth.request.InformacoesLoginUsuario;

public interface ConsultarUsuarioPort {
	
	InformacoesLoginUsuario findById(UUID id);
	
	InformacoesLoginUsuario findByCpfOrEmailOrTelefone(String cpf, String email, String Telefone);
}
