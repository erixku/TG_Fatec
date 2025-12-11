package br.app.harppia.modulo.church.domain.request;

import java.util.UUID;

public record DeletarIgrejaRequest(UUID idIgreja, UUID exclusor) {

}
