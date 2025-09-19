package br.app.harppia.modulo.usuario.shared.entity;

import java.time.LocalDateTime;

import br.app.harppia.defaults.custom.annotations.ValidUF;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

/**
 * Essa classe representa a arquitetura da tabela 'Endereco' do banco de dados.
 * 
 * @author asher_ren
 * @since 15/08/2025
 */
@Entity(name = "tb_endereco")
@Table(name = "tb_endereco", schema = "auth")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, insertable = false)
	private LocalDateTime updatedAt;
	
	@Column(nullable = false)
	@Size(min = 8, max = 8)
	private String cep;
	
	@Column(nullable = false)
	@Size(min = 2, max = 2)
	@ValidUF
	private String uf;

	@Column(nullable = false)
	@Size(max = 100)
	private String cidade;

	@Column(nullable = false)
	@Size(max = 100)
	private String bairro;
	
	@Column(nullable = false)
	@Size(max = 100)
	private String logradouro;
	
	@Column(nullable = false)
	@Size(max = 5)
	private String numero;

	@Column(nullable = false)
	@Size(max = 30)
	private String complemento;

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the rua
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
}
