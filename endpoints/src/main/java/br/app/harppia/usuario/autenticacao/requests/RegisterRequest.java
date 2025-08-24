package br.app.harppia.usuario.autenticacao.requests;

import br.app.harppia.usuario.autenticacao.enums.UserRoles;

public record RegisterRequest(
	    String firstname,
	    String lastname,
	    String email,
	    String password,
	    UserRoles role) {
}
