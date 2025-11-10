package br.app.harppia.modulo.igreja.domain.request;

import java.util.UUID;

public record DeletarIgrejaRequest(UUID idIgreja, UUID exclusor) {

}
