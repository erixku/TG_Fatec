package br.app.harppia.usuario.login.dto;

/**
 * Essa classe é responsável por receber e transportar as informações enviadas
 * pelo cliente para o servidor, contendo apenas o essencial para a
 * funcionalidade de LOGIN.
 */
public class LoginUsuarioDTO {
	private String telefone;
	private String cpf;
	private String email;
	private String senha;

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		if(telefone.trim().isEmpty())
			return;
		
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if(cpf.trim().isEmpty() || cpf.length() != 11)
			return;
		
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email.trim().isEmpty())
			return;

		this.email = email.trim();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (senha.trim().isEmpty())
			return;

		this.senha = senha.trim();
	}

}
