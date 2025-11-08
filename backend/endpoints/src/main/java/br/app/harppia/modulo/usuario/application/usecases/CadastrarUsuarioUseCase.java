package br.app.harppia.modulo.usuario.application.usecases;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.CadastroUsuarioException;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import br.app.harppia.modulo.usuario.application.port.out.RegistrarArquivoPort;
import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilInfo;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastradoDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastroDTO;
import br.app.harppia.modulo.usuario.infrasctructure.mapper.UsuarioMapper;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class CadastrarUsuarioUseCase {
	/**
	 * Classe responsável por intermediar as requisições com o back-end e validar os
	 * dados recebidos.
	 * 
	 * @author asher_ren
	 * @since 15/08/2025
	 */
	private final RegistrarArquivoPort registrarArquivoPort;
	private final PasswordEncoder passwdEncoder;
	private final UsuarioMapper userMapper;
	private final UsuarioRepository usuarioRepository;
	private final EntityManager entityManager;

	public CadastrarUsuarioUseCase(UsuarioRepository usuarioRepository, RegistrarArquivoPort registrarArquivoPort,
			PasswordEncoder passwdEncoder, UsuarioMapper userMapper, EntityManager entityManager) {
		this.usuarioRepository = usuarioRepository;
		this.registrarArquivoPort = registrarArquivoPort;
		this.passwdEncoder = passwdEncoder;
		this.userMapper = userMapper;
		this.entityManager = entityManager;
	}

	/**
	 * Recebe um DTO com todos os dados de cadastro do usuário, verifica se ele já
	 * existe na base de dados e, caso não exista, o cadastra. Caso ele já exista,
	 * não faz nada.
	 * 
	 * @param dto os dados do usuário
	 * @return Um objeto com UUID, email e nome do usuário cadastrado.
	 */
	@Transactional
	public UsuarioCadastradoDTO execute(UsuarioCadastroDTO dto, MultipartFile file) {
		
		UsuarioEntity userToSave = null;

		// Caso haja valores incoerentes, deve ser erro de mapeamento DTO <--> Entidade
		userToSave = userMapper.toEntity(dto);

		entityManager.createNativeQuery("SET ROLE r_anonimo;").executeUpdate();

		Optional<UsuarioEntity> result = usuarioRepository.findByCpfOrEmailOrTelefone(userToSave.getCpf(),
				userToSave.getEmail(), userToSave.getTelefone());

		if (result.isPresent())
			throw new CadastroUsuarioException("Esse usuário já existe!");

		userToSave.setSenha(passwdEncoder.encode(dto.senha()));

		entityManager
				.createNativeQuery("SET CONSTRAINTS storage.fk_s_storage_t_tb_arquivo_c_created_by DEFERRED;")
				.executeUpdate();

		UsuarioEntity savedUser = usuarioRepository.save(userToSave);

		if (file != null && !file.isEmpty()) {
			FotoPerfilInfo fotoSalva;
			fotoSalva = registrarArquivoPort.registrarFotoPerfilUsuario(file,
					ENomeBucket.FOTO_PERFIL_USUARIO.getCustomValue(), savedUser.getId());

			if(fotoSalva == null)
				throw new CadastroUsuarioException("Houve um erro ao submeter o arquivo. Verifique-o e tente novamente.");
			
			savedUser.setIdFotoPerfil(fotoSalva.id());

			usuarioRepository.save(savedUser);
		}

		entityManager.createNativeQuery("RESET ROLE;").executeUpdate();
		
		return new UsuarioCadastradoDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getNome());
	}
}
