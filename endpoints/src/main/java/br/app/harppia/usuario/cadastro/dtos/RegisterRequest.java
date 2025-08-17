package br.app.harppia.controllers.auth.usuario.dto;

import br.app.harppia.model.enums.UserRoles;

public record RegisterRequest(
	    String firstname,
	    String lastname,
	    String email,
	    String password,
	    UserRoles role) {

}
