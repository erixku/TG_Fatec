package br.app.harppia.modulo.userconfig.domain.request;

import java.util.UUID;

public record AtualizarAcessibilidadeAuditivaRequest(UUID idDonoConfig, Boolean alertasVisuais,
		Character intensidadeFlash, Boolean modoFlash, Boolean transcricaoAudio, Boolean vibracaoAprimorada) {

}
