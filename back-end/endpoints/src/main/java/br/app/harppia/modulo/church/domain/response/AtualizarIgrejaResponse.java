package br.app.harppia.modulo.church.domain.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record AtualizarIgrejaResponse(UUID idIgreja, String nome) {

}
