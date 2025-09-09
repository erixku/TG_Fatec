package br.app.harppia.usuario.cadastro.dto;

import br.app.harppia.defaults.shared.enums.NomeBucket;
import br.app.harppia.defaults.shared.interfaces.ToEntityParser;
import br.app.harppia.usuario.shared.entity.Bucket;
import jakarta.validation.constraints.NotNull;

public record BucketCadastroDTO(
		@NotNull(message = "Nome do bucket é obrigatório!") NomeBucket nome
	) implements ToEntityParser {

	@Override
	public Object toEntity() {
		Bucket bucket = new Bucket();
		bucket.setNome(this.nome);
		return bucket;
	}
}
