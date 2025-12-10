package br.app.harppia.defaults.custom.roles;

import java.util.Arrays;

public enum EChurchAndMinistryRoles {
    
    MEMBRO("membro", 0),
    ADMINISTRADOR("administrador", 1),
    ADM_PROPRIETARIO("adm_proprietario", 2);

    private final String roleKey; 
    private final int nivel;

    EChurchAndMinistryRoles(String roleKey, int nivel) {
        this.roleKey = roleKey;
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public String getRoleKey() {
        return roleKey;
    }
    
    public boolean temPermissaoMinima(EChurchAndMinistryRoles perfilNecessario) {
        if (perfilNecessario == null) return false;
        return this.nivel >= perfilNecessario.getNivel();
    }
    
    public static EChurchAndMinistryRoles fromValue(String text) {
        return Arrays.stream(EChurchAndMinistryRoles.values())
                .filter(role -> role.roleKey.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Role desconhecida: " + text));
    }
}