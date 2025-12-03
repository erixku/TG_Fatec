package br.app.harppia.modulo.usuario.application.usecases;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.usuario.application.port.out.RegistrarFotoPerfilUsuarioPort;
import br.app.harppia.modulo.usuario.application.port.out.SalvarConfiguracoesAcessibilidadePort;
import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilUsuarioRVO;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastradoDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastroDTO;
import br.app.harppia.modulo.usuario.domain.valueobject.IdUsuarioCVO;
import br.app.harppia.modulo.usuario.infrasctructure.mapper.UsuarioMapper;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class CadastrarUsuarioUseCase {

	private final RegistrarFotoPerfilUsuarioPort regFotoPrfUsrPort;
	private final SalvarConfiguracoesAcessibilidadePort slvCfgAcbPort;
	private final PasswordEncoder pwdEnc;
	private final UsuarioMapper usrMpr;
	private final UsuarioRepository usrRep;
	private final EntityManager entMng;

	public CadastrarUsuarioUseCase(RegistrarFotoPerfilUsuarioPort regFotoPrfUsrPort,
			SalvarConfiguracoesAcessibilidadePort slvCfgAcbPort, PasswordEncoder pwdEnc, 
			UsuarioMapper usrMpr, UsuarioRepository usrRep, EntityManager entMng) {
		this.regFotoPrfUsrPort = regFotoPrfUsrPort;
		this.slvCfgAcbPort = slvCfgAcbPort;
		this.pwdEnc = pwdEnc;
		this.usrMpr = usrMpr;
		this.usrRep = usrRep;
		this.entMng = entMng;
	}

	/**
	 * Recebe um DTO com todos os dados de cadastro do usuário, verifica se ele já
	 * existe na base de dados e, caso não exista, o cadastra. Caso ele já exista,
	 * não faz nada.
	 * 
	 * @param reqDto os dados do usuário
	 * @return Um objeto com UUID, email e nome do usuário cadastrado.
	 */
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	@Transactional
	public UsuarioCadastradoDTO execute(UsuarioCadastroDTO reqDto, MultipartFile mptFile) {

		UsuarioEntity usrEntSanitized = null;

		// Caso haja valores incoerentes, pode ser erro de mapeamento DTO <--> Entidade
		usrEntSanitized = usrMpr.toEntity(reqDto);

		Optional<IdUsuarioCVO> result = usrRep.findIdUsuarioByCpfOrEmailOrTelefone(usrEntSanitized.getCpf(),
				usrEntSanitized.getEmail(), usrEntSanitized.getTelefone());

		if (result.isPresent())
			throw new GestaoUsuarioException("Esse usuário já existe!");

		usrEntSanitized.setSenha(pwdEnc.encode(reqDto.senha()));

		entMng.createNativeQuery("SET CONSTRAINTS storage.fk_s_storage_t_tb_arquivo_c_created_by DEFERRED;")
				.executeUpdate();

		UsuarioEntity usrEntSaved = usrRep.save(usrEntSanitized);

		if (mptFile != null && !mptFile.isEmpty()) {
			FotoPerfilUsuarioRVO fotoSalva;

			fotoSalva = regFotoPrfUsrPort.persistir(mptFile, usrEntSaved.getId());

			if (fotoSalva == null)
				throw new GestaoUsuarioException("Houve um erro ao submeter o arquivo. Verifique-o e tente novamente.");

			usrRep.updateFotoById(fotoSalva.id(), usrEntSaved.getId());
		}

		slvCfgAcbPort.todas(usrEntSaved.getId());

		return new UsuarioCadastradoDTO(usrEntSaved.getId(), usrEntSaved.getEmail(), usrEntSaved.getNome());
	}
}
