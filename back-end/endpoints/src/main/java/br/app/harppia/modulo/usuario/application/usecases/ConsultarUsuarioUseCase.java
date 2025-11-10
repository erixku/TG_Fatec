package br.app.harppia.modulo.usuario.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.defaults.custom.sanitizers.CpfSanitizer;
import br.app.harppia.defaults.custom.sanitizers.EmailSanitizer;
import br.app.harppia.defaults.custom.sanitizers.TelefoneSanitizer;
import br.app.harppia.modulo.usuario.domain.dto.InformacaoPublicaUsuarioDTO;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;
import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesPublicasVO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;

@Service
public class ConsultarUsuarioUseCase {

	private final UsuarioRepository userRepo;

	public ConsultarUsuarioUseCase(UsuarioRepository userRepo) {
		this.userRepo = userRepo;
	}

	@UseRole(role = DatabaseRoles.ROLE_USUARIO)
	@Transactional(readOnly = true)
	public InformacaoPublicaUsuarioDTO buscarUsuarioPorCpfOuEmailOuTelefone(String key) {

	    if (key == null || key.trim().isEmpty()) {
	        throw new GestaoUsuarioException("Ao menos um identificador deve ser declarado!");
	    }

	    String cpf = "";
	    String email = "";
	    String telefone = "";

	    try {
	        email = EmailSanitizer.sanitize(key);
	    } catch (Exception ex) {}

	    try {
	        cpf = CpfSanitizer.sanitize(key);
	    } catch (Exception ex) {}

	    try {
	        telefone = TelefoneSanitizer.sanitize(key);
	    } catch (Exception ex) {}

	    if (cpf.isEmpty() && email.isEmpty() && telefone.isEmpty()) {
	        throw new GestaoUsuarioException("Identificador inválido ou em formato não reconhecido!");
	    }

	    Optional<BuscarInformacoesPublicasVO> user = userRepo.findIdUsuarioByCpfOrEmailOrTelefone(cpf, email, telefone);
	    
	    return (user.isEmpty()) ? null : new InformacaoPublicaUsuarioDTO(
	    											user.get().getId(),
	    											user.get().getNome(),
	    											user.get().getNomeSocial(),
	    											user.get().getEmail(),
	    											user.get().getIdFotoPerfil()
	    										);
	}

	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	@Transactional
	public InformacoesLoginUsuarioBanco buscarInformacoesLogin(String cpf, String email, String telefone) {

		Optional<UsuarioEntity> user = userRepo.findByCpfOrEmailOrTelefone(cpf, email, telefone);

		return (user.isEmpty()) ? null
				: new InformacoesLoginUsuarioBanco(user.get().getId(), user.get().getNome(), user.get().getCpf(),
						user.get().getEmail(), user.get().getTelefone(), user.get().getSenha());
	}

	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	@Transactional
	public InformacoesLoginUsuarioBanco buscarPorId(UUID id) {

		Optional<UsuarioEntity> user = userRepo.findById(id);

		return (user.isEmpty()) ? null
				: new InformacoesLoginUsuarioBanco(user.get().getId(), user.get().getNome(), user.get().getCpf(),
						user.get().getEmail(), user.get().getTelefone(), user.get().getSenha());
	}
}
