package br.app.harppia.modulo.usuario.application.usecases;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.harppia.modulo.usuario.domain.dto.AtualizarUsuarioDTO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import jakarta.transaction.Transactional;

@Service
public class AtualizarUsuarioUseCase {
	
	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passEncoder;
	
	
	
	public AtualizarUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passEncoder = passEncoder;
	}

	@Transactional
	public boolean execute(AtualizarUsuarioDTO newUserData) {
		
		Optional<UsuarioEntity> userData = usuarioRepository.findById(newUserData.uuidUsuario());
		
		if(userData.isEmpty())
			return false;
		
		// popular entidade com os novos valores		
		UsuarioEntity updatedUserData = atualizarObjetoDadosUsuario( newUserData, userData.get() );
		
		usuarioRepository.save(updatedUserData);
		
		return true;
	}

	private UsuarioEntity atualizarObjetoDadosUsuario(AtualizarUsuarioDTO newData, UsuarioEntity oldData) {
		
		UsuarioEntity userData = new UsuarioEntity();
		
		userData.setUpdatedAt( OffsetDateTime.now() );
		
		if( newData.nomeCompleto() != null ) {
			String[] nome = newData.nomeCompleto().split(" ", 1);
			
			userData.setNome		( (nome[0] != null) ? nome[0] : null );
			userData.setSobrenome	( (nome[1] != null) ? nome[1] : null );
		} else {
			userData.setNome( oldData.getNome() );
			userData.setSobrenome( oldData.getSobrenome() );
		}

		if( newData.nomeSocialCompleto() != null ) {
			String[] nomeSocial = newData.nomeSocialCompleto().split(" ", 1);
			
			userData.setNomeSocial		( (nomeSocial[0] != null) ? nomeSocial[0] : null );
			userData.setSobrenomeSocial	( (nomeSocial[1] != null) ? nomeSocial[1] : null );
		} else {
			userData.setNomeSocial( oldData.getNomeSocial() );
			userData.setSobrenomeSocial( oldData.getSobrenomeSocial() );
		}
			
		userData.setDataNascimento( 
				(newData.dataNascimento() != null) 
					? newData.dataNascimento() 
					: oldData.getDataNascimento() 		
			);
		
		userData.setTelefone( 
				(newData.telefone() != null)
					? newData.telefone()
					: oldData.getTelefone()
			);
		
		userData.setSenha( 
				(newData.senha() != null)
					? passEncoder.encode(newData.senha())
					: oldData.getSenha()
			);
				
		userData.setIdFotoPerfil( 
				(newData.uuidFotoPerfil() != null)
					? newData.uuidFotoPerfil()
					: oldData.getIdFotoPerfil()
			);
		
		return userData;
	}
}
