package br.app.harppia.modulo.auth.application.port.out;

import java.util.UUID;

public interface AtualizarUsuarioAuthToUsuarioPort {

	/**
	 * Chamado sempre que o usuário verificar a conta dele.
	 */
	public int marcarContaComoVerificada(UUID id);
}
