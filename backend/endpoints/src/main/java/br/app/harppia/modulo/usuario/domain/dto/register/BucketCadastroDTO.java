package br.app.harppia.modulo.usuario.domain.dto.register;

import br.app.harppia.modulo.shared.entity.storage.enums.ENomeBucket;
import jakarta.validation.constraints.NotNull;

public record BucketCadastroDTO(
		@NotNull(message = "Nome do bucket é obrigatório!") ENomeBucket nome
	) {
}
