package br.app.harppia.modulo.shared.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import br.app.harppia.defaults.custom.sanitizers.CpfSanitizer;
import br.app.harppia.defaults.custom.sanitizers.EmailSanitizer;
import br.app.harppia.defaults.custom.sanitizers.TelefoneSanitizer;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastroDTO;
import br.app.harppia.modulo.usuario.domain.entities.Usuario;

@Mapper(componentModel = "spring", uses = { ArquivoMapper.class, EnderecoMapper.class })
public abstract class UsuarioMapper {

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
	@Mapping(target = "fotoPerfil", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "lastAccess", ignore = true)
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
	
	/**
	 * Método para (após a conversão do DTO para entidade) converter o campo <b>`nomeCompleto`</b> (DTO)
	 * nos campos <b>`nome`</b> e <b>`sobrenome`</b> (entidade). O mesmo vale para nomeSocialCompleto.
	 * @param usuario o destino de onde será salvo o nome e sobrenome
	 * @param userDTO de onde virá os dados do nome completo
	 */
	@AfterMapping
    protected void preencherNomesCompletos(@MappingTarget Usuario usuario, UsuarioCadastroDTO userDTO) {
        // Lógica para Nome de Registro
        if (userDTO.nomeCompleto() != null && !userDTO.nomeCompleto().trim().isEmpty()) {
            String[] nomes = userDTO.nomeCompleto().trim().split("\\s+", 2);
            usuario.setNome(nomes[0]);
            if (nomes.length > 1) {
                usuario.setSobrenome(nomes[1]); // CORRIGIDO: setSobrenome
            }
        }
        
        // Lógica para Nome Social
        if (userDTO.nomeSocialCompleto() != null && !userDTO.nomeSocialCompleto().trim().isEmpty()) {
            String[] nomesSociais = userDTO.nomeSocialCompleto().trim().split("\\s+", 2);
            usuario.setNomeSocial(nomesSociais[0]);
            if (nomesSociais.length > 1) {
                usuario.setSobrenomeSocial(nomesSociais[1]);
            }
        }
    }
}
