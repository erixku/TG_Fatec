package br.app.harppia.usuario.autenticacao.dtos;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Classe destinada para o cadastro de novos usuários.
 */
public class UsuarioCadastroDTO {
	
	@NotBlank(message = "CPF é obrigatório!")
	@Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos.")
	private String cpf;
	
	@NotBlank(message = "Nome é obrigatório!")
	@Size(min = 2, max = 20, message = "Nome deve ter entre 2 e 20 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome só pode conter letras e espaços")
	private String nome;
	
	@NotBlank(message = "Sobrenome é obrigatório!")
	@Size(min = 2, max = 50, message = "Sobrenome deve ter entre 2 e 50 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]*$", message = "Sobrenome só pode conter letras e espaços")
	private String sobrenome;
	
	private String nomeSocial, sobrenomeSocial;
	
	@NotNull(message = "Sexo inválido!")
	private Character sexo;
	
	@NotNull(message = "Data de nascimento é obrigatória!")
	@Past(message = "Data de nascimento inválida.")
	private LocalDate dataAniversario;
	
	@NotBlank(message = "Email é obrigatório!")
	@Email(message = "Email deve ser válido.")
	@Size(max = 50, message = "Tamanho limite do email: 50 dígitos.")
	private String email;
	
	@NotBlank(message = "Telefone é obrigatório!")
	@Size(min = 11, max = 25, message = "Telefone deve ter entre 11 e 25 dígitos.")
	@Pattern(regexp = "^\\+\\d{1,3}\\s\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}", message = "Telefone deve estar no formato '+55 (99) 99999-9999'.")
	private String telefone;
	
	@NotBlank(message = "Senha é obrigatória!")
    @Size(min = 8, max = 128, message = "Senha deve conter entre 8 e 128 caracteres.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{12,128}$",
        message = "Senha deve conter letras maiúsculas, minúsculas, número e caractere especial."
    )
	private String senha;
	
	@NotNull(message = "O endereço é obrigatório!")
	@Valid
	private EnderecoCadastroDTO endereco;
	
	@Valid
	private ArquivoCadastroDTO arquivo;

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}

	/**
	 * @param sobrenome the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	/**
	 * @return the nomeSocial
	 */
	public String getNomeSocial() {
		return nomeSocial;
	}

	/**
	 * @param nomeSocial the nomeSocial to set
	 */
	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	/**
	 * @return the sobreNomeSocial
	 */
	public String getSobrenomeSocial() {
		return sobrenomeSocial;
	}

	/**
	 * @param sobreNomeSocial the sobreNomeSocial to set
	 */
	public void setSobrenomeSocial(String sobreNomeSocial) {
		this.sobrenomeSocial = sobreNomeSocial;
	}

	/**
	 * @return the sexo
	 */
	public Character getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the dataNascimento
	 */
	public LocalDate getDataNascimento() {
		return dataAniversario;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataAniversario = dataNascimento;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the enderecoId
	 */
	public EnderecoCadastroDTO getEndereco() {
		return endereco;
	}

	/**
	 * @param enderecoId the enderecoId to set
	 */
	public void setEndereco(EnderecoCadastroDTO endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the arquivoId
	 */
	public ArquivoCadastroDTO getArquivo() {
		return arquivo;
	}

	/**
	 * @param arquivoId the arquivoId to set
	 */
	public void setArquivo(ArquivoCadastroDTO arquivo) {
		this.arquivo = arquivo;
	}
}
