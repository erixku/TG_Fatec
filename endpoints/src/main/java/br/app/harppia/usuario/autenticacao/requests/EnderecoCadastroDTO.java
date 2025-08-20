package br.app.harppia.usuario.autenticacao.dtos;

import br.app.harppia.usuario.cadastro.entities.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoCadastroDTO {

	@NotBlank(message = "CEP é obrigatório!")
	@Size(min = 8, max = 8, message = "O CEP deve ter 8 dígitos!")
	@Pattern(regexp = "^\\d{8}$", message = "O CEP deve ter apenas números (ex: 12345012)!")
	private String cep;
	
	@NotBlank(message = "UF é obrigatório!")
	@Size(min = 2, max = 2, message = "O UF deve ter 2 dígitos!")
	@Pattern(regexp = "^[A-Z]{2}", message = "O UF deve ter apenas letras ('SP', 'RJ', ...)!")
	private String uf;

	@NotBlank(message = "Cidade é obrigatória!")
	@Size(max = 100, message = "Tamanho limite do nome da cidade: 100 dígitos.")
	private String cidade;

	@NotBlank(message = "Bairro é obrigatório!")
	@Size(max = 100, message = "Tamanho limite do nome do bairro: 100 dígitos.")
	private String bairro;
	
	@NotBlank(message = "Rua é obrigatória!")
	@Size(max = 100, message = "Tamanho limite do nome da rua: 100 dígitos.")
	private String rua;
	
	@NotBlank(message = "Número é obrigatório!")
	@Size(max = 5, message = "Tamanho limite do número: 5 dígitos.")
	private String numero;
	
	
	public Endereco getEnderecoComoEntidade() {
		
		Endereco end = new Endereco();
		end.setCep(this.getCep());
		end.setUf(this.getUf());
		end.setBairro(this.getBairro());
		end.setCidade(this.getCidade());
		end.setRua(this.getRua());
		end.setNumero(this.getNumero());
		
		return end;
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
	public String getRua() {
		return rua;
	}

	/**
	 * @param rua the rua to set
	 */
	public void setRua(String rua) {
		this.rua = rua;
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
