package br.app.harppia.modulo.usuario.autenticacao.dto;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UsuarioAutenticacaoTokenDTO(
		UUID uuid,
		String nome,
		String login,
		Collection<? extends GrantedAuthority> roles
		) implements UserDetails {

	/**
	 * Retorna a lista de autoridades que o usuário possui.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.isEmpty() ? null : roles();
	}

	/** 
	 * Esse DTO não porta a senha, portanto sempre retornará null.
	 */
	@Override
	public String getPassword() {
		return null;
	}
	
	/**
	 * Retorna o login do usuário.
	 */
	@Override
	public String getUsername() {
		return login.trim().isEmpty() ? null : login();
	}
}
