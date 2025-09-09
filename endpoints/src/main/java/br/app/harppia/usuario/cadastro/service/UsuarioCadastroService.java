package br.app.harppia.usuario.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.harppia.usuario.cadastro.dto.UsuarioCadastradoDTO;
import br.app.harppia.usuario.cadastro.dto.UsuarioCadastroDTO;
import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.usuario.shared.repository.UsuarioRepository;
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

		Usuario userToSave;

		try {
			userToSave = (Usuario) dto.toEntity();

			List<Usuario> result = usuarioRepository.findByEmail(dto.email());

			if (!result.isEmpty())
				throw new Exception("O usuário já existe!");

			Usuario savedUser = usuarioRepository.save(userToSave);

			return new UsuarioCadastradoDTO(savedUser.getUuid(), savedUser.getEmail(), savedUser.getNome());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}
}
