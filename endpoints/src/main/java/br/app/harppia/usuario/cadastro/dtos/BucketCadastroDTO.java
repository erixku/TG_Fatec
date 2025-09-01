package br.app.harppia.usuario.cadastro.dtos;

import br.app.harppia.defaults.shared.interfaces.ToEntityParser;
import br.app.harppia.usuario.cadastro.entities.Bucket;
import br.app.harppia.usuario.cadastro.enums.NomeBucket;
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
