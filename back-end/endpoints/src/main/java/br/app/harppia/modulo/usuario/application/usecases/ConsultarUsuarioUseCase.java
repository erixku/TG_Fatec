package br.app.harppia.modulo.usuario.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.defaults.custom.sanitizers.CpfSanitizer;
import br.app.harppia.defaults.custom.sanitizers.EmailSanitizer;
import br.app.harppia.defaults.custom.sanitizers.TelefoneSanitizer;
import br.app.harppia.modulo.usuario.domain.dto.InformacaoPublicaUsuarioDTO;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;
import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesAutenticacaoIVO;
import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesPublicasIVO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;

@Service
public class ConsultarUsuarioUseCase {

	private final UsuarioRepository userRepo;

	public ConsultarUsuarioUseCase(UsuarioRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_USUARIO)
	public InformacaoPublicaUsuarioDTO porCpfOuEmailOuTelefone(String key) {

		if (key == null || key.trim().isEmpty()) {
			throw new GestaoUsuarioException("Ao menos um identificador deve ser declarado!");
		}

		String cpf = "";
		String email = "";
		String telefone = "";

		try {
			email = EmailSanitizer.sanitize(key);
		} catch (Exception ex) {
		}

		try {
			cpf = CpfSanitizer.sanitize(key);
		} catch (Exception ex) {
		}

		try {
			telefone = TelefoneSanitizer.sanitize(key);
		} catch (Exception ex) {
		}

		if (cpf.isEmpty() && email.isEmpty() && telefone.isEmpty()) {
			throw new GestaoUsuarioException("Identificador inválido ou em formato não reconhecido!");
		}

		Optional<BuscarInformacoesPublicasIVO> user = userRepo.findPublicInfoByCpfOrEmailOrTelefone(cpf, email,
				telefone);

		return (user.isEmpty()) ? null
				: new InformacaoPublicaUsuarioDTO(user.get().getId(), user.get().getNome(), user.get().getNomeSocial(),
						user.get().getEmail(), user.get().getIdFotoPerfil());
	}

	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesLoginUsuarioBanco informacoesAutenticacaoLogin(String cpf, String email, String telefone) {

		Optional<BuscarInformacoesAutenticacaoIVO> user = userRepo.findAuthInfoByCpfOrEmailOrTelefone(cpf, email,
				telefone);

		return (user.isEmpty()) ? null
				: new InformacoesLoginUsuarioBanco(user.get().getId(), user.get().getEmail(), user.get().getSenha());
	}

	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesLoginUsuarioBanco porId(UUID id) {

		Optional<BuscarInformacoesAutenticacaoIVO> user = userRepo.findAuthInfoById(id);

		return (user.isEmpty()) ? null
				: new InformacoesLoginUsuarioBanco(user.get().getId(), user.get().getEmail(), user.get().getSenha());
	}
	
	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public UUID porEmail(String email) {

		Optional<UUID> user = userRepo.findIdByEmail(email);

		return (user.isEmpty()) ? null : user.get();
	}
}
