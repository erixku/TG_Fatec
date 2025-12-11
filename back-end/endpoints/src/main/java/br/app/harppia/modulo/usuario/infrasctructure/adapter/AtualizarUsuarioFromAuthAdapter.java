package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.auth.application.port.out.AtualizarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.usuario.application.usecases.AtualizarUsuarioUseCase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AtualizarUsuarioFromAuthAdapter implements AtualizarUsuarioAuthToUsuarioPort {

	private final AtualizarUsuarioUseCase atuUsrUC;

	@Override
	public int marcarContaComoVerificada(UUID id) {
		return atuUsrUC.marcarUsuarioComoAtivo(id);
	}
}
