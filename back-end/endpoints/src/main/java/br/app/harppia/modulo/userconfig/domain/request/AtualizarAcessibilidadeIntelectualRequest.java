package br.app.harppia.modulo.userconfig.domain.request;

import java.util.UUID;

public record AtualizarAcessibilidadeIntelectualRequest(UUID idDonoConfig, Boolean feedbackImediato, Boolean modoFoco,
		Character tamanhoIcone) {

}
