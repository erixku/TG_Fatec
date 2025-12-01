package br.app.harppia.modulo.igreja.domain.request;

import java.util.UUID;

public record CadastroIgrejaRequest(
		// Dados da igreja
		UUID idCriador, String cnpj, String nome, 
		String denominacao, String outraDenominacao, 
		UUID idFoto, UUID idDono,
		
		// Dados do endereço da igreja
		CadastroEnderecoIgrejaRequest cadEndIgrReq) {
}
