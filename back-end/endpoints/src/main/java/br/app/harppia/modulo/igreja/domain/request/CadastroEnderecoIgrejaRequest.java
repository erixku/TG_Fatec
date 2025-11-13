package br.app.harppia.modulo.igreja.domain.request;

public record CadastroEnderecoIgrejaRequest(String cep, String uf, String cidade, String bairro, String logradouro,
		String numero, String complemento, Boolean isEnderecoPrincipal) {

}
