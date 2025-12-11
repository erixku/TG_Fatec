package br.app.harppia.modulo.auth.domain.request;

import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioRequest(
		
		String telefone, 
		String cpf, 
		String email, 
		
		@NotBlank
		String senha) {
}
