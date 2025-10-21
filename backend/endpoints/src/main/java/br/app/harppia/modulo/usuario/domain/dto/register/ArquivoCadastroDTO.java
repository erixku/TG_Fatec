package br.app.harppia.modulo.usuario.domain.dto.register;

import br.app.harppia.modulo.shared.entity.storage.enums.EExtensaoArquivo;
import br.app.harppia.modulo.shared.entity.storage.enums.EMimeTypeArquivo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArquivoCadastroDTO (

	@NotBlank(message = "O path da imagem é obrigatório.")
	String nome,
	
	@NotNull(message = "O mime type do arquivo é obrigatório!")
	EMimeTypeArquivo mimeType,
	
	@NotNull(message = "A extensão do arquivo é obrigatória!")	
	EExtensaoArquivo extensao,
	
	@NotNull(message = "O tamanho (em bytes) é obrigatório!")
	Long tamanhoEmBytes,
	
	@NotNull(message = "O bucket é obrigatório!")
	@Valid 
	BucketCadastroDTO bucket
	) {
}
