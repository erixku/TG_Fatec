package br.app.harppia.usuario.cadastro.dto;

import java.time.LocalDate;

import br.app.harppia.defaults.custom.annotations.ValidCPF;
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
public record UsuarioCadastroDTO (
	
	@NotBlank(message = "CPF é obrigatório!")
	@ValidCPF
	String cpf,
	
	@NotBlank(message = "Nome completo é obrigatório!")
	@Size(min = 3, max = 70, message = "Nome completo deve ter entre 2 e 70 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome completo só pode conter letras e espaços")
	String nomeCompleto,
	
	@Size(min = 3, max = 70, message = "Nome social deve ter entre 3 e 70 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]*$", message = "Nome social só pode conter letras e espaços")	
	String nomeSocialCompleto, 
	
	@NotNull(message = "Sexo inválido!")
	Character sexo,
	
	@NotNull(message = "Data de nascimento é obrigatória!")
	@Past(message = "Data de nascimento inválida.")
	LocalDate dataNascimento,
	
	@NotBlank(message = "Email é obrigatório!")
	@Email(message = "Email deve ser válido.")
	@Size(max = 50, message = "Tamanho limite do email: 50 dígitos.")
	String email,
	
	@NotBlank(message = "Telefone é obrigatório!")
	@Size(min = 11, max = 25, message = "Telefone deve ter entre 11 e 25 dígitos.")
	@Pattern(regexp = "^\\+\\d{1,3}\\s\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}", message = "Telefone deve estar no formato '+55 (99) 99999-9999'.")
	String telefone,
	
	@NotBlank(message = "Senha é obrigatória!")
    @Size(min = 8, max = 128, message = "Senha deve conter entre 8 e 128 caracteres.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,128}$",
        message = "Senha deve conter letras maiúsculas, minúsculas, número e caractere especial."
    )
	String senha,
	
	@NotNull(message = "O endereço é obrigatório!")
	@Valid
	EnderecoCadastroDTO endereco,
	
	@Valid
	ArquivoCadastroDTO arquivo
	) {
}
