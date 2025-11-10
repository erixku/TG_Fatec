package br.app.harppia.defaults.custom.roles;

public enum DatabaseRoles {
	ROLE_ANONIMO("r_anonimo"),
	ROLE_USUARIO("r_usuario"),
	ROLE_LEVITA("r_levita"),
	ROLE_MINISTRO("r_ministro"),
	ROLE_LIDER("r_lider"),
	ROLE_ADMINISTRADOR("r_administrador"),
	ROLE_API("r_api"),
	ROLE_SISTEMA("r_sistema"),
	ROLE_OWNER("neondb_owner");
	
	private String role;
	
	DatabaseRoles(String role) {
		this.role = role;
	}
	
	public String getValue() {
		return this.role;
	}
	
}
