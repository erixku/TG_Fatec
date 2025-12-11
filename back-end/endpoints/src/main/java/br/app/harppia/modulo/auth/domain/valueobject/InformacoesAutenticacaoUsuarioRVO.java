package br.app.harppia.modulo.auth.domain.valueobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PUBLIC)
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class InformacoesAutenticacaoUsuarioRVO implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String login;
	private String senha;
	private Collection<? extends GrantedAuthority> systemRoles;
	private List<RolesMembroPorIgrejaMinisterioRVO> churchRoles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return ( systemRoles == null || systemRoles.isEmpty() ) ? null : systemRoles;
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
