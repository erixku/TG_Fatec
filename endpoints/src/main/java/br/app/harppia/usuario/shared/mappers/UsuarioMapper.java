package br.app.harppia.usuario.shared.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.defaults.custom.sanitizers.CpfSanitizer;
import br.app.harppia.defaults.custom.sanitizers.EmailSanitizer;
import br.app.harppia.defaults.custom.sanitizers.TelefoneSanitizer;
import br.app.harppia.usuario.cadastro.dto.UsuarioCadastroDTO;

@Mapper(componentModel = "spring", uses = { ArquivoMapper.class, EnderecoMapper.class })
public abstract class UsuarioMapper {

	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
	
	// DTO -> Entidade
	@Mapping(source = "endereco", target = "endId")
	@Mapping(source = "arquivo", target = "arquivoUUID")

	// Campos do DTO que precisam ser tratados:
	@Mapping(source = "cpf", target = "cpf", qualifiedByName = "tratarCpf")
	@Mapping(source = "email", target = "email", qualifiedByName = "tratarEmail")
	@Mapping(source = "telefone", target = "telefone", qualifiedByName = "tratarTelefone")

	// Campos da classe `Usuario` a serem ignorados na conversão:
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "nome", ignore = true)
	@Mapping(target = "sobrenome", ignore = true)
	@Mapping(target = "nomeSocial", ignore = true)
	@Mapping(target = "sobrenomeSocial", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "ultimoAcesso", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	public abstract Usuario toEntity(UsuarioCadastroDTO dto);

	// MÉTODOS PARA TRATAR/FORMATAR OS DADOS
	
	/**
	 * Esse método remove tudo que não for dígitos númericos do CPF.
	 * 
	 * @param cpfMalFormatado - a String do CPF a ser tratado.
	 * @return uma String com apenas os números do CPF ou nulo se a entrada for
	 *         vazia.
	 */
	@Named("tratarCpf")
	protected String tratarCpf(String cpfMalFormatado) {
		return CpfSanitizer.sanitize(cpfMalFormatado);
	}

	/**
	 * Esse método tratará o email, removendo pontos desnecessários antes do '@' e o
	 * normalizará.
	 * 
	 * @param emailMalFormatado - email a ser tratado.
	 * @return nulo se email for vazio ou uma String normalizada e sem pontos antes
	 *         do arroba ('@').
	 */
	@Named("tratarEmail")
	protected String tratarEmail(String emailMalFormatado) {
		return EmailSanitizer.sanitize(emailMalFormatado);
    }
	
	/**
	 * Método para sanitizar o telefone, retornando uma string somente com dígitos
	 * numéricos.
	 * @param telefoneMalFormatado
	 * @return
	 */
	@Named("tratarTelefone")
	protected String tratarTelefone(String telefoneMalFormatado) {
		return TelefoneSanitizer.sanitize(telefoneMalFormatado);
	}
	
	@AfterMapping
	private void preencherNomeSobrenome(@MappingTarget Usuario usuario, UsuarioCadastroDTO userDTO) {
		
		if(userDTO.nomeCompleto().trim().isEmpty())
			throw new IllegalArgumentException("Erro ao separar o nome: o nome completo é obrigatório!");
		
		String[] nomes = userDTO.nomeCompleto().split("\\s+", 2);
		
		if(nomes.length > 0)
			usuario.setNome(nomes[0]);
		else
			throw new IllegalArgumentException("Falha ao mapear: o nome não pode ser nulo!");
		
		if(nomes.length > 1)
			usuario.setNome(nomes[1]);
		else 
			throw new IllegalArgumentException("Falha ao mapear: o nome não pode ser nulo!");
			
	}
	
	@AfterMapping
	private void preencherNomeSobrenomeSociais(@MappingTarget Usuario usuario, UsuarioCadastroDTO userDTO) {
		
		if(userDTO.nomeCompleto().trim().isEmpty())
			throw new IllegalArgumentException("Erro ao separar nome social: o nome social completo é obrigatório!");
		
		String[] nomes = userDTO.nomeCompleto().split("\\s+", 2);
		
		usuario.setNomeSocial((nomes.length > 0) ? nomes[0] : null);
		usuario.setSobrenomeSocial((nomes.length > 1) ? nomes[1] : null);
	}
}
