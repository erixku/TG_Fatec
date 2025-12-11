package br.app.harppia.modulo.userconfig.domain.request;

import java.util.UUID;

import br.app.harppia.modulo.userconfig.infrastructure.repository.enums.ECorTema;
import br.app.harppia.modulo.userconfig.infrastructure.repository.enums.ETipoDaltonismo;

public record AtualizarAcessibilidadeVisualRequest(UUID idDonoConfig, Boolean altoContraste,
		Character intensidadeDaltonismo, ETipoDaltonismo modoDaltonismo, Boolean negrito, Boolean removerAnimacoes,
		Character tamanhoTexto, ECorTema tema, Boolean vibrarAoTocar) {

}
