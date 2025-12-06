package br.app.harppia.modulo.auth.domain.valueobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import lombok.Builder;

@Builder
public record InformacoesAutenticacaoUsuarioRVO (
		UUID id,
		String login,
		String senha,
		Collection<? extends GrantedAuthority> rolesGerais,
		List<RolesMembroPorIgrejaMinisterioRVO> rolesIgreja
	) implements UserDetails {
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return ( rolesGerais == null || rolesGerais.isEmpty() ) ? null : rolesGerais();
	}

	@Override
	public String getPassword() {
		return senha;
	}
	
	@Override
	public String getUsername() {
		return login;
	}
}
