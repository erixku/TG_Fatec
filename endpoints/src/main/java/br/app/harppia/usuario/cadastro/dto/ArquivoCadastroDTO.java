package br.app.harppia.usuario.cadastro.dto;

import br.app.harppia.defaults.shared.enums.ExtensaoArquivo;
import br.app.harppia.defaults.shared.enums.MimeTypeArquivo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArquivoCadastroDTO (

	@NotBlank(message = "O path da imagem é orbigatório.")
	String nome,
	
	@NotNull(message = "O mime type do arquivo é obrigatório!")
	MimeTypeArquivo mimeType,
	
	@NotNull(message = "A extensão do arquivo é obrigatória!")	
	ExtensaoArquivo extensao,
	
	@NotNull(message = "O tamanho (em bytes) é obrigatório!")
	Long tamanhoEmBytes,
	
	@NotNull(message = "O bucket é obrigatório!")
	@Valid 
	BucketCadastroDTO bucket
	) {
}
