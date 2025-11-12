package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.auth.request.InformacoesAutenticacaoUsuario;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;

@Component
public class ConsultarUsuarioAdapter implements ConsultarUsuarioPort {
	private ConsultarUsuarioUseCase cuuc;

	public ConsultarUsuarioAdapter(ConsultarUsuarioUseCase conUsrUC) {
		this.cuuc = conUsrUC;
	}
	
	@Override
	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuario informacoesAutenticacao(String cpf, String email, String telefone) {

		InformacoesLoginUsuarioBanco user = cuuc.informacoesAutenticacaoLogin(cpf, email, telefone);

		// = = = = = = = = = = = = = = = = = = = = = =//
		// IMPLEMENTAR A BUSCA PELOS ROLES DO USUÁRIO //
		// = = = = = = = = = = = = = = = = = = = = = =//
		// ...
		if(user == null) return null;
		
		return new InformacoesAutenticacaoUsuario(user.id(), user.email(), user.senha(),
				List.of(new SimpleGrantedAuthority("LEVITA")));
	}

	@Override
	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuario porId(UUID id) {
		InformacoesLoginUsuarioBanco user = cuuc.porId(id);

		if(user == null) 
			return null;
		
		return new InformacoesAutenticacaoUsuario(user.id(), user.email(), user.senha(),
				List.of(new SimpleGrantedAuthority("LEVITA")));
	}

}
