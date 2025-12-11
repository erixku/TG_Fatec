package br.app.harppia.modulo.usuario.domain.dto.register;

import br.app.harppia.modulo.usuario.infrasctructure.annotation.ValidUF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Essa classe é destinada a transferência de dados segura entre o cliente e o servidor.
 * Ela deve conter apenas dados sobre o endereço a ser cadastrado.
 * Por ser um record, o JPA já automatiza muita coisa (como getters e setters),
 * por isso, essa classe têm apenas um método declarado.
 */
public record EnderecoCadastroDTO (
	@NotBlank(message = "CEP é obrigatório!")
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
	) {
}
