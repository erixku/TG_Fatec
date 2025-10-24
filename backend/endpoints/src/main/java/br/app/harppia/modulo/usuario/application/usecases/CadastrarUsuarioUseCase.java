package br.app.harppia.modulo.usuario.application.usecases;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import br.app.harppia.modulo.usuario.application.port.out.RegistrarArquivoPort;
import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilInfo;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastradoDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastroDTO;
import br.app.harppia.modulo.usuario.infrasctructure.mapper.UsuarioMapper;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
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

	public CadastrarUsuarioUseCase(UsuarioRepository usuarioRepository, RegistrarArquivoPort registrarArquivoPort,
			PasswordEncoder passwdEncoder, UsuarioMapper userMapper) {
		this.usuarioRepository = usuarioRepository;
		this.registrarArquivoPort = registrarArquivoPort;
		this.passwdEncoder = passwdEncoder;
		this.userMapper = userMapper;
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

		try {
			// Caso algo retorne null, deve ser erro de mapeamento DTO <--> Entidade
			userToSave = userMapper.toEntity(dto);

			List<UsuarioEntity> result = usuarioRepository.findByEmail(dto.cpf());

			if (!result.isEmpty())
				throw new Exception("O usuário já existe!");

			userToSave.setSenha(passwdEncoder.encode(dto.senha()));

			if (file != null && file.isEmpty()) {
				FotoPerfilInfo fotoSalva = registrarArquivoPort.registrarFotoPerfilUsuario(file,
						ENomeBucket.FOTO_PERFIL_USUARIO.getCustomValue() + "/");

				userToSave.setIdFotoPerfil(fotoSalva.id());
			}

			UsuarioEntity savedUser = usuarioRepository.save(userToSave);

			return new UsuarioCadastradoDTO(savedUser.getUuid(), savedUser.getEmail(), savedUser.getNome());
		} catch (Exception ex) {
			System.out.println("\n\nHouve um erro ao registrar.\n");
			System.out.println(ex.getMessage());
			System.out.println(ex.getLocalizedMessage());
		}

		return null;
	}
}
