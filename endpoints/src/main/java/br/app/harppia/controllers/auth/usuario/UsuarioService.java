package br.app.harppia.endpoints.controllers.auth.usuario;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import br.app.harppia.endpoints.model.auth.dto.UsuarioCadastroDTO;
import br.app.harppia.endpoints.model.auth.entities.Usuario;
import br.app.harppia.endpoints.model.storage.entities.Arquivo;
import br.app.harppia.endpoints.persistence.auth.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Transactional
	public int cadastrarUsuario(UsuarioCadastroDTO dto) {
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
	    
	    
        Arquivo fotoUsuario = new Arquivo();
        if(dto.getArquivo() == null) {
        	// popular arquivo de foto guest
        }
        else {
        	// popular arquivo com dados do dto
        	fotoUsuario.setCaminho("foto_padrao_" + dto.getCpf().concat(dto.getArquivo().getExtensaoArquivo().getExtension()));
        }
        usuario.setArquivoUUID(fotoUsuario);

	    return usuarioRepository.cadastrarUsuario(usuario);
	}

}
