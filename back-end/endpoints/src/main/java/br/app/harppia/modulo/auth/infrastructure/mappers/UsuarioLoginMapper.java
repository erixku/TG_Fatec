package br.app.harppia.modulo.auth.infrastructure.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.app.harppia.modulo.auth.domain.request.LoginUsuarioRequest;
import br.app.harppia.modulo.auth.domain.valueobject.InformacoesLoginSanitizadasRVO;
import br.app.harppia.modulo.usuario.infrasctructure.sanitizer.CpfSanitizer;
import br.app.harppia.modulo.usuario.infrasctructure.sanitizer.EmailSanitizer;
import br.app.harppia.modulo.usuario.infrasctructure.sanitizer.TelefoneSanitizer;

@Mapper(componentModel = "spring")
public abstract class UsuarioLoginMapper {

	/**
	 * Este método, além de mapear, também já sanitiza alguns dados.
	 * Estes são: CPF, email, telefone e senha.
	 * A senha é apenas encriptada.
	 * @param dto
	 * @return
	 */
	@Mapping(source = "cpf", target = "cpf", qualifiedByName = "sanitizarCpf")
	@Mapping(source = "email", target = "email", qualifiedByName = "sanitizarEmail")
	@Mapping(source = "telefone", target = "telefone", qualifiedByName = "sanitizarTelefone")
	@Mapping(source = "senha", target = "senha", qualifiedByName = "encriptarSenha")
	public abstract InformacoesLoginSanitizadasRVO mapRequest(LoginUsuarioRequest lgnUsrReqMapped);

	// --------------------------------------------
	// MÉTODOS PARA TRATAR/FORMATAR OS DADOS
	// --------------------	
	@Named("sanitizarCpf")
	protected String tratarCpf(String cpfMalFormatado) {
		return (cpfMalFormatado == null || cpfMalFormatado.trim().isEmpty())
				? null
				: CpfSanitizer.sanitize(cpfMalFormatado);
	}

	@Named("sanitizarEmail")
	protected String tratarEmail(String emailMalFormatado) {
		return (emailMalFormatado == null || emailMalFormatado.trim().isEmpty())
				? null
				: EmailSanitizer.sanitize(emailMalFormatado);
    }
	
	@Named("sanitizarTelefone")
	protected String tratarTelefone(String telefoneMalFormatado) {
		return (telefoneMalFormatado == null || telefoneMalFormatado.trim().isEmpty())
				? null
				: TelefoneSanitizer.sanitize(telefoneMalFormatado);
	}

	@Named("encriptarSenha")
	protected String encriptarSenha(String senhaPura) {
		return (senhaPura == null || senhaPura.trim().isEmpty())
				? null
				: senhaPura;
	}
}
