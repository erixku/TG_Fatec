package br.app.harppia.modulo.ministry.infraestructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum EFuncaoMembro implements EnumPersistivel {
	LEVITA("levita", 0),
	MINISTRO("ministro", 1),
	LIDER("lider", 2);

	private String funcao;
	private int nivel;
	
	EFuncaoMembro(String funcao, int nivel){
		this.funcao = funcao;
		this.nivel = nivel;
	}

	public int getNivel() {
		return this.nivel;
	}
	
	@JsonValue
	@Override
	public String getCustomValue() {
		return this.funcao;
	}
	
    @JsonCreator
    public static EFuncaoMembro fromValue(String value) {
    	return InternalEnumParser.fromValue(EFuncaoMembro.class, value);
    }
    
    public boolean temPermissaoMinima(EFuncaoMembro perfilNecessario) {
        if (perfilNecessario == null) return false;
        return this.nivel >= perfilNecessario.getNivel();
    }
}