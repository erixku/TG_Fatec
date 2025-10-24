package br.app.harppia.modulo.auth.domain.request;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record InformacoesLoginUsuario (
		UUID uuid,
		String nome,
		String login,
		String senha,
		Collection<? extends GrantedAuthority> roles
		) implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.isEmpty() ? null : roles();
	}

	@Override
	public String getPassword() {
		return senha;
	}
	
	@Override
	public String getUsername() {
		return login.trim().isEmpty() ? null : login();
	}
}
