package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;

@Component
public class ConsultarUsuarioAuthAdapter implements ConsultarUsuarioAuthToUsuarioPort {

	private final ConsultarUsuarioUseCase conUsrUC;

	private final List<SimpleGrantedAuthority> DEFAULT_ROLES;

	public ConsultarUsuarioAuthAdapter(ConsultarUsuarioUseCase conUsrUC) {
		this.conUsrUC = conUsrUC;

		DEFAULT_ROLES = List.of(new SimpleGrantedAuthority("LIDER"));
	}

	@Override
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO informacoesAutenticacao(String cpf, String email, String telefone) {

		InformacoesLoginUsuarioBanco user = conUsrUC.informacoesAutenticacaoLogin(cpf, email, telefone);

		// = = = = = = = = = = = = = = = = = = = = = =//
		// IMPLEMENTAR A BUSCA PELOS ROLES DO USUÁRIO //
		// = = = = = = = = = = = = = = = = = = = = = =//
		// ...
		List<SimpleGrantedAuthority> roles = DEFAULT_ROLES;

		if (user == null)
			return null;

		return new InformacoesAutenticacaoUsuarioRVO(user.id(), user.email(), user.senha(), roles);
	}

	@Override
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO porId(UUID id) {

		InformacoesLoginUsuarioBanco user = conUsrUC.porId(id);

		List<SimpleGrantedAuthority> roles = DEFAULT_ROLES;

		return (user == null) ? null
				: new InformacoesAutenticacaoUsuarioRVO(user.id(), user.email(), user.senha(), roles);
	}

	@Override
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO porEmail(String email) {

		InformacoesLoginUsuarioBanco infLgnUsrBnc = conUsrUC.porEmail(email);

		// implementar a busca por roles
		List<SimpleGrantedAuthority> roles = DEFAULT_ROLES;
		
		return (infLgnUsrBnc != null)
				? new InformacoesAutenticacaoUsuarioRVO(
						infLgnUsrBnc.id(), infLgnUsrBnc.email(), 
						infLgnUsrBnc.senha(), roles)
				: null;
	}

	@Override
	public UUID idPorEmail(String email) {
		return conUsrUC.idPorEmail(email);
	}

}
