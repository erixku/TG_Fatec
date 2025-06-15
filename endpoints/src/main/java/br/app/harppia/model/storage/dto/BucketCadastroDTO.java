package br.app.harppia.model.storage.dto;

import br.app.harppia.model.enums.NomeBucket;
import br.app.harppia.model.storage.entities.Bucket;
import jakarta.validation.constraints.NotNull;

public class BucketCadastroDTO {
	
	@NotNull(message = "Nome do bucket é obrigatório!")
	private NomeBucket nome;
	
	private Integer tempoLimiteUpload;
	private Long tamanhoMinimo;
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
	public Long getTamanhoMinimo() {
		return tamanhoMinimo;
	}
	/**
	 * @param tamanhoMinimo the tamanhoMinimo to set
	 */
	public void setTamanhoMinimo(Long tamanhoMinimo) {
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
	
	public Bucket parseToBucket() {
		Bucket bucket = new Bucket();
		
		bucket.setNome				(this.getNome() 				== null ? null : this.getNome());
		bucket.setTempoLimiteUpload	(this.getTempoLimiteUpload() 	== null ? null : this.getTempoLimiteUpload());
		bucket.setTamanhoMaximo		(this.getTamanhoMaximo() 		== null ? null : this.getTamanhoMaximo());
		bucket.setTamanhoMinimo		(this.getTamanhoMinimo() 		== null ? null : this.getTamanhoMinimo());
		
		return bucket;
	}
}
