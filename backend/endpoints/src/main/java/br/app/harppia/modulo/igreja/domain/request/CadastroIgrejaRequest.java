package br.app.harppia.modulo.igreja.domain.request;

import java.util.UUID;

import br.app.harppia.modulo.igreja.infrastructure.repository.enums.EDenominacaoIgreja;

public record CadastroIgrejaRequest(String cnpj, String nomeIgreja, UUID idDono, EDenominacaoIgreja denominacao) {

}
