package br.app.harppia.modulo.igreja.domain.request;

import java.util.UUID;

import br.app.harppia.modulo.igreja.infrastructure.repository.enums.EDenominacaoIgreja;

public record CadastroIgrejaRequest(
		// Dados da igreja
		UUID idCriador, String cnpj, String nome, EDenominacaoIgreja denominacao, String outraDenominacao, UUID idFoto,
		UUID idDono,
		
		// Dados do endereço da igreja
		CadastroEnderecoIgrejaRequest cadEndIgrReq) {
}
