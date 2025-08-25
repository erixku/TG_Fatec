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
		
		if(usuarioRepository.findBy)
		
	    Usuario usuario = new Usuario();

	    usuario.setCpf(dto.getCpf());
	    usuario.setNome(dto.getNome());
	    usuario.setSobrenome(dto.getSobrenome());
	    usuario.setNomeSocial(dto.getNomeSocial());
	    usuario.setSobrenomeSocial(dto.getSobrenomeSocial());

	    String sexo = dto.getSexo().toString().toUpperCase();
	    if (!sexo.equals("F") && !sexo.equals("M") && !sexo.equals("O"))
	        throw new IllegalArgumentException("Sexo inválido ou desconhecido.");
	    usuario.setSexo(sexo.charAt(0));

	    if (dto.getDataNascimento() == null || !dto.getDataNascimento().isBefore(LocalDate.now()))
	        throw new IllegalArgumentException("Data de nascimento inválida.");
	    usuario.setDataNascimento(dto.getDataNascimento());

	    usuario.setEmail(dto.getEmail());
	    usuario.setTelefone(dto.getTelefone());
	    usuario.setSenha(dto.getSenha());
	    
	    if (dto.getEndereco() == null)
	        throw new IllegalArgumentException("Endereço obrigatório.");
	    usuario.setEndId(dto.getEndereco().getEnderecoComoEntidade());
	    
	    
	    /* ÊNFASE AQUI NESTE MÉTODO */
	    // Caso tenha foto, insere-a primeiro, depois salva o usuário
        if(!(dto.getArquivo() == null)) {
        	Arquivo archive = dto.getArquivo().parseToArquivo();
        	NomeBucket nome = archive.getBucket().getNome();
        	
        	Bucket bucket = bucketRepository.findByNome(nome.getValorCustomizado())
        			.orElseThrow( () -> new IllegalStateException("Nome de bucket inválido: ".concat(nome.getNome())) );
        	
        	archive.setBucket(bucket);
        	
        	usuario.setArquivoUUID(archive);
        }

	    return usuarioRepository.save(usuario);
	}
}
