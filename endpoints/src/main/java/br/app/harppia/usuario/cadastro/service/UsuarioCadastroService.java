package br.app.harppia.usuario.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.harppia.usuario.cadastro.dto.UsuarioCadastradoDTO;
import br.app.harppia.usuario.cadastro.dto.UsuarioCadastroDTO;
import br.app.harppia.usuario.shared.entity.Arquivo;
import br.app.harppia.usuario.shared.entity.Bucket;
import br.app.harppia.usuario.shared.entity.Endereco;
import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.usuario.shared.mappers.ArquivoMapper;
import br.app.harppia.usuario.shared.mappers.EnderecoMapper;
import br.app.harppia.usuario.shared.mappers.UsuarioMapper;
import br.app.harppia.usuario.shared.repository.BucketRepository;
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
	
	@Autowired
	private BucketRepository bucketRepository;
	
	private final UsuarioMapper userMapper;
	private final EnderecoMapper enderecoMapper;
	private final ArquivoMapper arquivoMapper;
	
	public UsuarioCadastroService(UsuarioMapper userMapper, EnderecoMapper enderecoMapper, ArquivoMapper arquivoMapper) {
		this.userMapper = userMapper;
		this.enderecoMapper = enderecoMapper;
		this.arquivoMapper = arquivoMapper;
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

		Usuario userToSave;

		try {
			
			System.out.println(dto.arquivo().mimeType().getValorCustomizado());
			
			userToSave = userMapper.toEntity(dto);

			System.out.println(userToSave.getArquivoUUID().getMimeType().getValorCustomizado());

			List<Usuario> result = usuarioRepository.findByEmail(dto.cpf());

			if (!result.isEmpty())
				throw new Exception("O usuário já existe!");

			// tenta persistir o user
			Usuario savedUser = usuarioRepository.save(userToSave);
			
			return new UsuarioCadastradoDTO(savedUser.getUuid(), savedUser.getEmail(), savedUser.getNome());

		} catch (Exception ex) {
			System.out.println("Houve um erro ao registrar." + ex.getMessage());
		}

		return null;
	}
	
	public Usuario toEntity(UsuarioCadastroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setSexo(dto.sexo());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setSenha(dto.senha());
        
        Endereco endereco = enderecoMapper.toEntity(dto.endereco()); 
        usuario.setEndId(endereco);

        if (dto.arquivo() != null) {
            Arquivo arquivo = arquivoMapper.toEntity(dto.arquivo());
            Bucket bucket = bucketRepository
                .findByNome(arquivo.getBucket().getNome().getValorCustomizado())
                .orElseThrow(() -> new IllegalStateException("Bucket inválido"));
            arquivo.setBucket(bucket);
            usuario.setArquivoUUID(arquivo);
        }

        return usuario;
    }
}
