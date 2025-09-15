package br.app.harppia.defaults.shared.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

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
	public String getCustomValue() {
		return this.role;
	}
}
