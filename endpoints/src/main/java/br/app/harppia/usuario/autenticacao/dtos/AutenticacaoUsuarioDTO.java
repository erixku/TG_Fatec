package br.app.harppia.usuario.autenticacao.dtos;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AutenticacaoUsuarioDTO implements UserDetails {

	private String username;
	private String password;
	private String email;
	private String cpf;
	private String telefone;
	
	public AutenticacaoUsuarioDTO(String nome, String senha, String telefone, String email, String cpf) {
		this.username = nome;
		this.password = senha;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
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
		return username;
	}

}
