package br.app.harppia.modulo.usuario.domain.dto.register;

import java.time.LocalDate;

import br.app.harppia.modulo.usuario.infrasctructure.annotation.ValidCPF;
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
	
	@ValidCPF
	String cpf,
	
	@NotBlank(message = "Nome completo é obrigatório!")
	@Size(min = 3, max = 70, message = "Nome completo deve ter entre 2 e 70 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome completo só pode conter letras e espaços")
	String nomeCompleto,
	
	@Size(min = 3, max = 70, message = "Nome social deve ter entre 3 e 70 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]*$", message = "Nome social só pode conter letras e espaços")	
	String nomeSocialCompleto,
	
	@NotNull(message = "Sexo ausente!")
	String sexo,
	
	@NotNull(message = "Data de nascimento é obrigatória!")
	@Past(message = "Data de nascimento inválida.")
	LocalDate dataNascimento,
	
	@Email(message = "Email deve ser válido.")
	@Size(max = 50, message = "Tamanho limite do email: 50 dígitos.")
	String email,
	
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
	EnderecoCadastroDTO endereco
	) {
}
