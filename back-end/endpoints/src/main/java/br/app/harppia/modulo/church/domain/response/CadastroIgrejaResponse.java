package br.app.harppia.modulo.church.domain.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CadastroIgrejaResponse(UUID idIgreja, String nome, UUID idDono) {

}
