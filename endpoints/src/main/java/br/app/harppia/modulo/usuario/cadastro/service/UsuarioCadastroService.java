package br.app.harppia.modulo.usuario.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.harppia.modulo.usuario.cadastro.dto.UsuarioCadastradoDTO;
import br.app.harppia.modulo.usuario.cadastro.dto.UsuarioCadastroDTO;
import br.app.harppia.modulo.usuario.shared.entity.Usuario;
import br.app.harppia.modulo.usuario.shared.mappers.UsuarioMapper;
import br.app.harppia.modulo.usuario.shared.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

/**
 * Classe responsável por intermediar as requisições com o back-end e validar os
 * dados recebidos.
 * 
 * @author asher_ren
 * @since 15/08/2025
 */
@Service
public class UsuarioCadastroService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private final PasswordEncoder passwdEncoder;
	private final UsuarioMapper userMapper;
	
	public UsuarioCadastroService(PasswordEncoder passwdEncoder, UsuarioMapper userMapper) {
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
	public UsuarioCadastradoDTO cadastrarUsuario(UsuarioCadastroDTO dto) {

		Usuario userToSave = null;
		
		try {
			// Caso algo retorne null, deve ser erro de mapeamento DTO <--> Entidade
			userToSave = userMapper.toEntity(dto);
			 
			List<Usuario> result = usuarioRepository.findByEmail(dto.cpf());

			if (!result.isEmpty())
				throw new Exception("O usuário já existe!");
			
			userToSave.setSenha(passwdEncoder.encode(dto.senha()));
			
			// tenta persistir o user
			Usuario savedUser = usuarioRepository.save(userToSave);
			
			return new UsuarioCadastradoDTO(savedUser.getUuid(), savedUser.getEmail(), savedUser.getNome());
		} catch (Exception ex) {
			System.out.println("\n\nHouve um erro ao registrar.\n");
			System.out.println(ex.getMessage());
			System.out.println(ex.getLocalizedMessage());
		}

		return null;
	}
}
