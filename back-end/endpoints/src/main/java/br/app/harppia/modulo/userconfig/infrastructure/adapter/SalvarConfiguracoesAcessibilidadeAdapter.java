package br.app.harppia.modulo.userconfig.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.userconfig.application.usecases.SalvarConfiguracoesAcessibilidadeUseCase;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeAuditivaRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeIntelectualRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeVisualRequest;
import br.app.harppia.modulo.usuario.application.port.out.SalvarConfiguracoesAcessibilidadePort;

@Component
public class SalvarConfiguracoesAcessibilidadeAdapter implements SalvarConfiguracoesAcessibilidadePort {

	private final SalvarConfiguracoesAcessibilidadeUseCase scaus;

	public SalvarConfiguracoesAcessibilidadeAdapter(SalvarConfiguracoesAcessibilidadeUseCase scaus) {
		this.scaus = scaus;
	}

	/**
	 * Esse método só deve ser usado simutâneamente ao cadastro de um usuário
	 * @param idDonoConfig o UUID do usuário dono dessas configurações
	 */
	@Override
	public void todas(UUID idDonoConfig) {
		scaus.todas(
				new SalvarAcessibilidadeAuditivaRequest(idDonoConfig),
				new SalvarAcessibilidadeVisualRequest(idDonoConfig),
				new SalvarAcessibilidadeIntelectualRequest(idDonoConfig)
			);
	}
}
