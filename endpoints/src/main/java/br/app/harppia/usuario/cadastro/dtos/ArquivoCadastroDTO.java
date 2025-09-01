package br.app.harppia.usuario.cadastro.dtos;

import br.app.harppia.defaults.shared.interfaces.ToEntityParser;
import br.app.harppia.usuario.cadastro.entities.Arquivo;
import br.app.harppia.usuario.cadastro.entities.Bucket;
import br.app.harppia.usuario.cadastro.enums.ExtensaoArquivo;
import br.app.harppia.usuario.cadastro.enums.MimeTypeArquivo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArquivoCadastroDTO (

	@NotBlank(message = "O path da imagem é orbigatório.")
	String caminho,
	
	@NotNull(message = "O mime type do arquivo é obrigatório!")
	MimeTypeArquivo tipoArquivo,
	
	@NotNull(message = "A extensão do arquivo é obrigatória!")	
	ExtensaoArquivo extensaoArquivo,
	
	@NotNull(message = "O tamanho (em bytes) é obrigatório!")
	Long tamanhoEmBytes,
	
	@NotNull(message = "O bucket é obrigatório!")
	@Valid 
	BucketCadastroDTO bucketArquivo
	) implements ToEntityParser {

	@Override
	public Arquivo toEntity() {
		Arquivo arq = new Arquivo();
		
		arq.setCaminho			(this.caminho 			== null ? null : this.caminho);
		arq.setExtensao			(this.extensaoArquivo 	== null ? null : this.extensaoArquivo);
		arq.setMimeType			(this.tipoArquivo		== null ? null : this.tipoArquivo);
		arq.setTamanhoEmBytes	(this.tamanhoEmBytes 	== null ? null : this.tamanhoEmBytes);
		arq.setBucket			(this.bucketArquivo 	== null ? null : (Bucket) this.bucketArquivo.toEntity());
		
		return arq;
	}
}
