package br.app.harppia.modulo.igreja.domain.request;

import java.util.UUID;

public record AtualizarIgrejaRequest(UUID idIgreja, String nome) {

}
