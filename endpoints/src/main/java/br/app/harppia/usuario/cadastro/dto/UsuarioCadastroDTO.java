package br.app.harppia.usuario.cadastro.dto;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import br.app.harppia.defaults.shared.enums.NomeBucket;
import br.app.harppia.defaults.shared.interfaces.ToEntityParser;
import br.app.harppia.usuario.shared.entity.Arquivo;
import br.app.harppia.usuario.shared.entity.Bucket;
import br.app.harppia.usuario.shared.entity.Endereco;
import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.usuario.shared.repository.BucketRepository;
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
	@Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos.")
	String cpf,
	
	@NotBlank(message = "Nome é obrigatório!")
	@Size(min = 2, max = 20, message = "Nome deve ter entre 2 e 20 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome só pode conter letras e espaços")
	String nome,
	
	@NotBlank(message = "Sobrenome é obrigatório!")
	@Size(min = 2, max = 50, message = "Sobrenome deve ter entre 2 e 50 dígitos.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]*$", message = "Sobrenome só pode conter letras e espaços")
	String sobrenome,
	
	String nomeSocial, 
	String sobrenomeSocial,
	
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
	) implements ToEntityParser {

	@Autowired
	private static BucketRepository bucketRepository;
	
	@Override
	public Object toEntity() {
		Usuario usuario = new Usuario();

	    usuario.setCpf(this.cpf);
	    usuario.setNome(this.nome);
	    usuario.setSobrenome(this.sobrenome);
	    usuario.setNomeSocial(this.nomeSocial);
	    usuario.setSobrenomeSocial(this.sobrenomeSocial);

	    String sexo = this.sexo.toString().toUpperCase();
	    if (!sexo.equals("F") && !sexo.equals("M") && !sexo.equals("O"))
	        throw new IllegalArgumentException("Sexo inválido ou desconhecido.");
	    usuario.setSexo(sexo.charAt(0));

	    if (this.dataNascimento == null || !this.dataNascimento.isBefore(LocalDate.now()))
	        throw new IllegalArgumentException("Data de nascimento inválida.");
	    usuario.setDataNascimento(this.dataNascimento);

	    usuario.setEmail(this.email);
	    usuario.setTelefone(this.telefone);
	    usuario.setSenha(this.senha);
	    
	    if (this.endereco == null)
	        throw new IllegalArgumentException("Endereço obrigatório.");
	    usuario.setEndId((Endereco) this.endereco.toEntity());
	    
	    
	    /* ÊNFASE AQUI NESTE MÉTODO */
	    // Caso tenha foto, insere-a primeiro, depois salva o usuário
        if(!(this.arquivo == null)) {
        	Arquivo archive = arquivo.toEntity();
        	NomeBucket nome = archive.getBucket().getNome();
        	
        	Bucket bucket = bucketRepository.findByNome(nome.getValorCustomizado())
        			.orElseThrow( () -> new IllegalStateException("Nome de bucket inválido: ".concat(nome.getNome())) );
        	
        	archive.setBucket(bucket);
        	usuario.setArquivoUUID(archive);
        }
        
        return usuario;
	}
}
