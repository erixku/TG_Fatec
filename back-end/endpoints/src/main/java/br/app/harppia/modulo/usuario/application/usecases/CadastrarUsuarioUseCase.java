package br.app.harppia.modulo.usuario.application.usecases;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import br.app.harppia.modulo.usuario.application.port.out.RegistrarArquivoPort;
import br.app.harppia.modulo.usuario.application.port.out.SalvarConfiguracoesAcessibilidadePort;
import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilInfo;
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

	private final RegistrarArquivoPort rap;
	private final SalvarConfiguracoesAcessibilidadePort scap;
	private final PasswordEncoder pe;
	private final UsuarioMapper um;
	private final UsuarioRepository ur;
	private final EntityManager em;

	public CadastrarUsuarioUseCase(RegistrarArquivoPort rap, SalvarConfiguracoesAcessibilidadePort scap,
			PasswordEncoder pe, UsuarioMapper um, UsuarioRepository ur, EntityManager em) {
		this.rap = rap;
		this.scap = scap;
		this.pe = pe;
		this.um = um;
		this.ur = ur;
		this.em = em;
	}

	/**
	 * Recebe um DTO com todos os dados de cadastro do usuário, verifica se ele já
	 * existe na base de dados e, caso não exista, o cadastra. Caso ele já exista,
	 * não faz nada.
	 * 
	 * @param reqDto os dados do usuário
	 * @return Um objeto com UUID, email e nome do usuário cadastrado.
	 */
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	@Transactional
	public UsuarioCadastradoDTO execute(UsuarioCadastroDTO reqDto, MultipartFile mptFile) {

		UsuarioEntity usrEntSanitized = null;

		// Caso haja valores incoerentes, pode ser erro de mapeamento DTO <--> Entidade
		usrEntSanitized = um.toEntity(reqDto);

		Optional<IdUsuarioCVO> result = ur.findIdUsuarioByCpfOrEmailOrTelefone(usrEntSanitized.getCpf(),
				usrEntSanitized.getEmail(), usrEntSanitized.getTelefone());

		if (result.isPresent())
			throw new GestaoUsuarioException("Esse usuário já existe!");

		usrEntSanitized.setSenha(pe.encode(reqDto.senha()));

		em.createNativeQuery("SET CONSTRAINTS storage.fk_s_storage_t_tb_arquivo_c_created_by DEFERRED;")
				.executeUpdate();

		UsuarioEntity usrEntSaved = ur.save(usrEntSanitized);

		if (mptFile != null && !mptFile.isEmpty()) {
			FotoPerfilInfo fotoSalva;
			fotoSalva = rap.registrarFotoPerfilUsuario(mptFile, ENomeBucket.FOTO_PERFIL_USUARIO.getCustomValue(),
					usrEntSaved.getId());

			if (fotoSalva == null)
				throw new GestaoUsuarioException("Houve um erro ao submeter o arquivo. Verifique-o e tente novamente.");

			ur.updateFotoById(fotoSalva.id(), usrEntSaved.getId());
		}
		
		scap.todas(usrEntSaved.getId());

		return new UsuarioCadastradoDTO(usrEntSaved.getId(), usrEntSaved.getEmail(), usrEntSaved.getNome());
	}
}
