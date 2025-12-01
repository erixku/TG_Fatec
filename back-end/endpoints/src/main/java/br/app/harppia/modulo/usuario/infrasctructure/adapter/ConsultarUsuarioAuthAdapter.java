package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthPort;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;

@Component
public class ConsultarUsuarioAuthAdapter implements ConsultarUsuarioAuthPort {
	private ConsultarUsuarioUseCase conUsrUC;

	public ConsultarUsuarioAuthAdapter(ConsultarUsuarioUseCase conUsrUC) {
		this.conUsrUC = conUsrUC;
	}
	
	@Override
	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO informacoesAutenticacao(String cpf, String email, String telefone) {

		InformacoesLoginUsuarioBanco user = conUsrUC.informacoesAutenticacaoLogin(cpf, email, telefone);

		// = = = = = = = = = = = = = = = = = = = = = =//
		// IMPLEMENTAR A BUSCA PELOS ROLES DO USUÁRIO //
		// = = = = = = = = = = = = = = = = = = = = = =//
		// ...
		if(user == null) return null;
		
		return new InformacoesAutenticacaoUsuarioRVO(user.id(), user.nome(), user.email(), user.senha(),
				List.of(new SimpleGrantedAuthority("LEVITA")));
	}

	@Override
	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO porId(UUID id) {
		InformacoesLoginUsuarioBanco user = conUsrUC.porId(id);
		
		return (user == null) 
				? null
				: new InformacoesAutenticacaoUsuarioRVO(user.id(), user.nome(), user.email(), user.senha(),
						List.of(new SimpleGrantedAuthority("LEVITA")));
	}

}
