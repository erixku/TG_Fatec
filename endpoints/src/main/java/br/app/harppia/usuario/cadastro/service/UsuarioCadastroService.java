package br.app.harppia.usuario.cadastro.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.harppia.usuario.cadastro.dtos.UsuarioCadastroDTO;
import br.app.harppia.usuario.cadastro.entities.Arquivo;
import br.app.harppia.usuario.cadastro.entities.Bucket;
import br.app.harppia.usuario.cadastro.entities.Usuario;
import br.app.harppia.usuario.cadastro.enums.NomeBucket;
import br.app.harppia.usuario.cadastro.repositorys.BucketRepository;
import br.app.harppia.usuario.cadastro.repositorys.UsuarioRepository;
import jakarta.transaction.Transactional;

/**
 * Classe responsável por intermediar as requisições com o back-end e
 * validar os dados recebidos.
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
	
	@Transactional
	public Usuario cadastrarUsuario(UsuarioCadastroDTO dto) {
		
		Usuario user = (Usuario) dto.toEntity();
		
		if(usuarioRepository.findBy)
		
	    

	    return usuarioRepository.save(usuario);
	}
}
