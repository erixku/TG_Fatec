package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

import br.app.harppia.modulo.auth.domain.valueobject.InformacoesAutenticacaoUsuarioRVO;

public interface ConsultarUsuarioAuthToUsuarioPort {
	UUID idPorEmail(String email);

	InformacoesAutenticacaoUsuarioRVO porId(UUID id);

	InformacoesAutenticacaoUsuarioRVO porEmail(String email);

	/**
	 * Usado para efetuar o login.
	 * Retorna o id, login, senha e roles de sistema e de cada igreja a
	 * qual o usuário faz parte.
	 * 
	 * @param cpf
	 * @param email
	 * @param Telefone
	 * @return id, login, senha, roles de sistema e de igreja/ministério
	 */
	InformacoesAutenticacaoUsuarioRVO informacoesAutenticacao(String cpf, String email, String Telefone);
}
