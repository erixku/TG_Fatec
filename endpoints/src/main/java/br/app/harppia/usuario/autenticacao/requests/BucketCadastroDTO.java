package br.app.harppia.usuario.autenticacao.dtos;

import br.app.harppia.usuario.cadastro.entities.Bucket;
import br.app.harppia.usuario.cadastro.enums.NomeBucket;
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
