package br.app.harppia.endpoints.model.storage.dto;

import br.app.harppia.endpoints.model.enums.NomeBucket;
import jakarta.validation.constraints.NotNull;

public class BucketCadastroDTO {
	
	@NotNull(message = "Nome do bucket é obrigatório!")
	private NomeBucket nome;
	
	private Integer tempoLimiteUpload;
	private Integer tamanhoMinimo;
	private Long tamanhoMaximo;
	
	/**
	 * @return the nome
	 */
	public NomeBucket getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(NomeBucket nome) {
		this.nome = nome;
	}
	/**
	 * @return the tempoLimiteUpload
	 */
	public Integer getTempoLimiteUpload() {
		return tempoLimiteUpload;
	}
	/**
	 * @param tempoLimiteUpload the tempoLimiteUpload to set
	 */
	public void setTempoLimiteUpload(Integer tempoLimiteUpload) {
		this.tempoLimiteUpload = tempoLimiteUpload;
	}
	/**
	 * @return the tamanhoMinimo
	 */
	public Integer getTamanhoMinimo() {
		return tamanhoMinimo;
	}
	/**
	 * @param tamanhoMinimo the tamanhoMinimo to set
	 */
	public void setTamanhoMinimo(Integer tamanhoMinimo) {
		this.tamanhoMinimo = tamanhoMinimo;
	}
	/**
	 * @return the tamanhoMaximo
	 */
	public Long getTamanhoMaximo() {
		return tamanhoMaximo;
	}
	/**
	 * @param tamanhoMaximo the tamanhoMaximo to set
	 */
	public void setTamanhoMaximo(Long tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}
}
