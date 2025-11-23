package br.app.harppia.modulo.usuario.infrasctructure.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import br.app.harppia.defaults.custom.sanitizers.CpfSanitizer;
import br.app.harppia.defaults.custom.sanitizers.EmailSanitizer;
import br.app.harppia.defaults.custom.sanitizers.TelefoneSanitizer;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastroDTO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;

@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public abstract class UsuarioMapper {
	
	// -------------------------
	// DTO -> Entidade de dominio
	// ---------

	// Campos do DTO que precisam ser tratados:
	@Mapping(source = "cpf", target = "cpf", qualifiedByName = "tratarCpf")
	@Mapping(source = "email", target = "email", qualifiedByName = "tratarEmail")
	@Mapping(source = "telefone", target = "telefone", qualifiedByName = "tratarTelefone")
	@Mapping(source = "sexo", target = "sexo", qualifiedByName = "tratarSexo")

	// Campos da classe `Usuario` a serem ignorados na conversão:
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "nome", ignore = true)
	@Mapping(target = "sobrenome", ignore = true)
	@Mapping(target = "nomeSocial", ignore = true)
	@Mapping(target = "sobrenomeSocial", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "lastAccess", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	@Mapping(target = "idFotoPerfil", ignore = true)
	public abstract UsuarioEntity toEntity(UsuarioCadastroDTO dto);

	// --------------------------------------------
	// MÉTODOS PARA TRATAR/FORMATAR OS DADOS
	// --------------------
	
	@Named("tratarCpf")
	protected String tratarCpf(String cpfMalFormatado) {
		return CpfSanitizer.sanitize(cpfMalFormatado);
	}

	@Named("tratarEmail")
	protected String tratarEmail(String emailMalFormatado) {
		return EmailSanitizer.sanitize(emailMalFormatado);
    }
	
	@Named("tratarTelefone")
	protected String tratarTelefone(String telefoneMalFormatado) {
		return TelefoneSanitizer.sanitize(telefoneMalFormatado);
	}

	@Named("tratarSexo")
	protected Character tratarSexo(String sex) {
		return sex.toUpperCase().charAt(0);
	}
	
	@AfterMapping
    protected void preencherNomesCompletos(@MappingTarget UsuarioEntity usuario, UsuarioCadastroDTO userDTO) {
        // Lógica para Nome e Sobrenome
        if (userDTO.nomeCompleto() != null && !userDTO.nomeCompleto().trim().isEmpty()) {
            String[] nomes = userDTO.nomeCompleto().trim().split("\\s+", 2);
            usuario.setNome(nomes[0]);
            if (nomes.length > 1) {
                usuario.setSobrenome(nomes[1]);
            }
        }
        
        // Lógica para Nome e Sobrenome Social
        if (userDTO.nomeSocialCompleto() != null && !userDTO.nomeSocialCompleto().trim().isEmpty()) {
            String[] nomesSociais = userDTO.nomeSocialCompleto().trim().split("\\s+", 2);
            usuario.setNomeSocial(nomesSociais[0]);
            if (nomesSociais.length > 1) {
                usuario.setSobrenomeSocial(nomesSociais[1]);
            }
        }
    }
}
