package br.app.harppia.modulo.auth.domain.valueobjects;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;

@Builder
public record InformacoesAutenticacaoUsuarioRVO (
		UUID id,
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
		return nome.trim().isEmpty() ? null : login();
	}
}
