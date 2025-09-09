package br.app.harppia.usuario.cadastro.dto;

import br.app.harppia.defaults.custom.annotations.ValidUF;
import br.app.harppia.defaults.shared.interfaces.ToEntityParser;
import br.app.harppia.usuario.shared.entity.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Essa classe é destinada a transferência de dados segura entre o cliente e o servidor.
 * Ela deve conter apenas dados sobre o endereço a ser cadastrado.
 * Por ser um record, o JPA já automatiza muita coisa (como getters e setters),
 * por isso, essa classe têm apenas um método declarado.
 */
public record EnderecoCadastroDTO (
	@NotBlank(message = "CEP é obrigatório!")
	@Size(min = 8, max = 8, message = "O CEP deve ter 8 dígitos!")
	@Pattern(regexp = "^\\d{8}$", message = "O CEP deve ter apenas números (ex: 12345012)!")
	String cep,
	
	@ValidUF
	String uf,

	@NotBlank(message = "Cidade é obrigatório!")
	@Size(max = 100, message = "Tamanho limite do nome da cidade: 100 dígitos.")
	String cidade,

	@NotBlank(message = "Bairro é obrigatório!")
	@Size(max = 100, message = "Tamanho limite do nome do bairro: 100 dígitos.")
	String bairro,
	
	@NotBlank(message = "Rua é obrigatória!")
	@Size(max = 100, message = "Tamanho limite do nome da rua: 100 dígitos.")
	String logradouro,
	
	@NotBlank(message = "Número é obrigatório!")
	@Size(max = 5, message = "Tamanho limite do número: 5 dígitos.")
	String numero,
	
	@Size(max = 30, message = "Tamanho limite do complemento: 30 dígitos.")
	String complemento
	
	) implements ToEntityParser{
	
	/**
	 * Esse método tentará converter esse DTO para sua respectiva entidade no
	 * banco de dados. É importante garantir que a classe {@Endereco} esteja atualizada
	 * e este DTO esteja de acordo com sua estrutura.
	 * @return uma instância da classe `Endereco` populada.
	 */
	@Override
	public Object toEntity() {
		Endereco end = new Endereco();
		end.setCep(this.cep);
		end.setUf(this.uf);
		end.setBairro(this.bairro);
		end.setCidade(this.cidade);
		end.setLogradouro(this.logradouro);
		end.setNumero(this.numero);
		end.setComplemento(this.complemento);
		return end;
	}
}
