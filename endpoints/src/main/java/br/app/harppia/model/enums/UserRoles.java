package br.app.harppia.model.enums;

import br.app.harppia.app.utils.rules.EnumPersistivel;

public enum UserRoles implements EnumPersistivel {
	ADMINISTRADOR ("Administrador"),
	LIDER 		("Líder"), 
	MINISTRO 	("Ministro"),
	LEVITA		("Levita");
	
	private String role;
	
	UserRoles(String role){
		this.role = role;
	}

	@Override
	public String getValorCustomizado() {
		return this.role;
	}
}
