package br.app.harppia.modulo.auth.application.port.out;

import br.app.harppia.modulo.auth.domain.request.InformacoesLoginUsuario;

public interface ConsultarUsuarioPort {
	InformacoesLoginUsuario findByCpfOrEmailOrTelefone(String cpf, String email, String Telefone);
}
