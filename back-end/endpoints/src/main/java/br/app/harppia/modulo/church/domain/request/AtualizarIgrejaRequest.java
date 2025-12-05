package br.app.harppia.modulo.church.domain.request;

import java.util.UUID;

public record AtualizarIgrejaRequest(UUID idIgreja, String nome) {

}
