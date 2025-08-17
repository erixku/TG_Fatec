package br.app.harppia.usuario.autenticacao.dtos;

import br.app.harppia.model.enums.UserRoles;

public record RegisterRequest(
	    String firstname,
	    String lastname,
	    String email,
	    String password,
	    UserRoles role) {

}
