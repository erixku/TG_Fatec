package br.app.harppia.modulo.auth.domain.request;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class AutenticacaoUsuarioDTO implements UserDetails {

	// Garante o versionamento correto dessa classe
	@Serial
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	
	public AutenticacaoUsuarioDTO(String login, String senha) {
		this.login = login;
		this.password = senha;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// =================================================================================
	    // --- ATENÇÃO: LÓGICA DE DERIVAÇÃO DE ROLE TEMPORÁRIA (DÍVIDA TÉCNICA) ---
	    // Esta lógica deve ser substituída por um campo 'role' no banco de dados
	    // antes de o sistema ir para produção.
	    // =================================================================================

	    // Para todos os outros usuários, a role padrão será LEVITA
	    return List.of(new SimpleGrantedAuthority("ROLE_LEVITA"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}
}
