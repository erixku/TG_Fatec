package br.app.harppia.modulo.usuario.cadastro.dto;

import br.app.harppia.defaults.shared.enums.NomeBucket;
import jakarta.validation.constraints.NotNull;

public record BucketCadastroDTO(
		@NotNull(message = "Nome do bucket é obrigatório!") NomeBucket nome
	) {
}
