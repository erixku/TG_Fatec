package br.app.harppia.model.storage.dto;

import br.app.harppia.model.enums.NomeBucket;
import br.app.harppia.model.storage.entities.Bucket;
import jakarta.validation.constraints.NotNull;

public class BucketCadastroDTO {
	
	@NotNull(message = "Nome do bucket é obrigatório!")
	private NomeBucket nome;

	
	/**
	 * @return the enum NomeBucket value
	 */
	public NomeBucket getNome() {
		return nome;
	}
	
	/**
	 * @param nome the enum NomeBucket value to set
	 */
	public void setNome(NomeBucket nome) {
		this.nome = nome;
	}

	
	public Bucket parseToBucket() {
		Bucket bucket = new Bucket();
		
		bucket.setNome(this.getNome() == null ? null : this.getNome());
		
		return bucket;
	}
}
