package br.app.harppia.defaults.custom.roles;

public enum ESystemRoles {
    ANONIMO(0),
    USUARIO(1);

    private final int nivel;

    ESystemRoles(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
    
    public  boolean temPermissaoMinima(ESystemRoles perfilNecessario) {
        return this.nivel >= perfilNecessario.getNivel();
    }
    
    public static ESystemRoles fromValue(int nivelAcesso) {
        for (ESystemRoles role : ESystemRoles.values()) {
            if (role.getNivel() == nivelAcesso) {
                return role;
            }
        }
        return ANONIMO;
    }

}