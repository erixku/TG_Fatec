package br.app.harppia.modulo.igreja.domain.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record BuscarIgrejaResponse(UUID idIgreja) {

}
