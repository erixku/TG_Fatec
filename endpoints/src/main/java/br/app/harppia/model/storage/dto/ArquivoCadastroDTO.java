package br.app.harppia.endpoints.model.storage.dto;

import br.app.harppia.endpoints.model.enums.ExtensaoArquivo;
import br.app.harppia.endpoints.model.enums.MimeTypeArquivo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArquivoCadastroDTO {

	@NotBlank(message = "O path da imagem é orbigatório.")
	private String caminho;
	
	@NotNull(message = "O mime type do arquivo é obrigatório!")
	private MimeTypeArquivo tipoArquivo;
	
	@NotNull(message = "A extensão do arquivo é obrigatória!")	
	private ExtensaoArquivo extensaoArquivo;
	
	@NotNull(message = "O tamanho (em bytes) é obrigatório!")
	private Long tamanhoEmBytes;
	
	@NotNull(message = "O bucket é obrigatório!")
	@Valid 
	private BucketCadastroDTO bucketArquivo;

	/**
	 * @return the caminho
	 */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * @param caminho the caminho to set
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	/**
	 * @return the tipoArquivo
	 */
	public MimeTypeArquivo getTipoArquivo() {
		return tipoArquivo;
	}

	/**
	 * @param tipoArquivo the tipoArquivo to set
	 */
	public void setTipoArquivo(MimeTypeArquivo tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	/**
	 * @return the extensaoArquivo
	 */
	public ExtensaoArquivo getExtensaoArquivo() {
		return extensaoArquivo;
	}

	/**
	 * @param extensaoArquivo the extensaoArquivo to set
	 */
	public void setExtensaoArquivo(ExtensaoArquivo extensaoArquivo) {
		this.extensaoArquivo = extensaoArquivo;
	}

	/**
	 * @return the tamanhoEmBytes
	 */
	public Long getTamanhoEmBytes() {
		return tamanhoEmBytes;
	}

	/**
	 * @param tamanhoEmBytes the tamanhoEmBytes to set
	 */
	public void setTamanhoEmBytes(Long tamanhoEmBytes) {
		this.tamanhoEmBytes = tamanhoEmBytes;
	}

	/**
	 * @return the bucketArquivoId
	 */
	public BucketCadastroDTO getBucketArquivo() {
		return bucketArquivo;
	}

	/**
	 * @param bucketArquivoId the bucketArquivoId to set
	 */
	public void setBucketArquivo(BucketCadastroDTO bucketArquivo) {
		this.bucketArquivo = bucketArquivo;
	}
	
	
}
