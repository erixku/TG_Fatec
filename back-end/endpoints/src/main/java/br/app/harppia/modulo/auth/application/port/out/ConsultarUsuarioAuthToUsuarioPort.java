package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;

public interface ConsultarUsuarioAuthToUsuarioPort {
	UUID idPorEmail(String email);

	InformacoesAutenticacaoUsuarioRVO porId(UUID id);

	InformacoesAutenticacaoUsuarioRVO porEmail(String email);

	InformacoesAutenticacaoUsuarioRVO informacoesAutenticacao(String cpf, String email, String Telefone);
}
