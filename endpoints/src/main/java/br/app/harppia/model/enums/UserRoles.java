package br.app.harppia.endpoints.model.enums;

public enum UserRoles {
	ADMINISTRADOR ("Administrador"),
	LIDER 		("Líder"), 
	MINISTRO 	("Ministro"),
	LEVITA		("Levita");
	
	private String role;
	
	UserRoles(String role){
		this.role = role;
	}
}
