package br.app.harppia.modulo.usuario.application.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.usuario.domain.dto.InformacaoPublicaUsuarioDTO;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;
import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesAutenticacaoIVO;
import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesPublicasIVO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.sanitizer.CpfSanitizer;
import br.app.harppia.modulo.usuario.infrasctructure.sanitizer.EmailSanitizer;
import br.app.harppia.modulo.usuario.infrasctructure.sanitizer.TelefoneSanitizer;

@Service
public class ConsultarUsuarioUseCase {

	private final UsuarioRepository userRepo;

	public ConsultarUsuarioUseCase(UsuarioRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_USUARIO)
	public InformacaoPublicaUsuarioDTO porCpfOuEmailOuTelefone(String key) {

		if (key == null || key.trim().isEmpty()) 
			throw new GestaoUsuarioException("Ao menos um identificador deve ser declarado!");

		String cpf = "";
		String email = "";
		String telefone = "";

		try {
			email = EmailSanitizer.sanitize(key);
		} catch (Exception ex) { }

		try {
			cpf = CpfSanitizer.sanitize(key);
		} catch (Exception ex) { }

		try {
			telefone = TelefoneSanitizer.sanitize(key);
		} catch (Exception ex) { }

		if (cpf.isEmpty() && email.isEmpty() && telefone.isEmpty()) 
			throw new GestaoUsuarioException("Identificador inválido ou em formato não reconhecido!");

		Optional<BuscarInformacoesPublicasIVO> usersFound = userRepo.findPublicInfoByCpfOrEmailOrTelefone(cpf, email,
				telefone);

		if(usersFound.isEmpty())
			throw new GestaoUsuarioException("Nenhum usuário encontrado!");
		
		BuscarInformacoesPublicasIVO user = usersFound.get();
		
		return new InformacaoPublicaUsuarioDTO(user.getId(), user.getNome(), user.getNomeSocial(),
						user.getEmail(), user.getIdFotoPerfil());
	}

	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesLoginUsuarioBanco informacoesAutenticacaoLogin(String cpf, String email, String telefone) {

		List<BuscarInformacoesAutenticacaoIVO> users = userRepo.findAuthInfoByCpfOrEmailOrTelefone(cpf, email,
				telefone);

		if(users.isEmpty())
			return null;
		
		if(users.size() > 1)
			System.err.println(users.toString());
		
		BuscarInformacoesAutenticacaoIVO user = users.getFirst();
			
		return new InformacoesLoginUsuarioBanco(user.getId(), user.getEmail(), user.getSenha());
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
	public UUID idPorEmail(String email) {

		List<UUID> user = userRepo.findIdByEmail(email);

		return (user.isEmpty()) ? null : user.getFirst();
	}

	@Transactional(readOnly = true)
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesLoginUsuarioBanco porEmail(String email) {

		List<BuscarInformacoesAutenticacaoIVO> users = userRepo.findByEmail(email);

		if(users.isEmpty()) return null;
		
		BuscarInformacoesAutenticacaoIVO user = users.getFirst();
		
		return new InformacoesLoginUsuarioBanco(user.getId(), user.getEmail(), user.getSenha());
	}
}
