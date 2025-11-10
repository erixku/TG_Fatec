package br.app.harppia.modulo.usuario.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.usuario.domain.request.DeletarUsuarioRequest;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;

@Service
public class DeletarUsuarioUseCase {
	private final UsuarioRepository ur;
	
	public DeletarUsuarioUseCase(UsuarioRepository ur) {
		this.ur = ur;
	}
	
	@UseRole(role = DatabaseRoles.ROLE_USUARIO)
	@Transactional
	public boolean deletar(DeletarUsuarioRequest requestDto) {
		
		if(requestDto == null)
			throw new GestaoUsuarioException("Nenhuma informação sobre o usuário a ser deletado!");
		
		if(!requestDto.idUsuarioParaApagar().equals(requestDto.idUsuarioQueApagou()))
			throw new GestaoUsuarioException("Somente o próprio usuário pode apagar sua conta!");
			
		ur.deleteById(requestDto.idUsuarioParaApagar());

		boolean isDeleted = ur.findById(requestDto.idUsuarioParaApagar()).isEmpty();
		
		return isDeleted;
	}
}
