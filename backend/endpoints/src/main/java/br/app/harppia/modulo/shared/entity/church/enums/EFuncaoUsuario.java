package br.app.harppia.modulo.shared.entity.church.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum EFuncaoUsuario implements EnumPersistivel {
	LIDER("lider"),
	MINISTRO("ministro"),
	LEVITA("levita");

	private String funcao;
	
	EFuncaoUsuario(String funcao){
		this.funcao = funcao;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.funcao;
	}
	
    @JsonCreator
    public static EFuncaoUsuario fromValue(String value) {
    	return InternalEnumParser.fromValue(EFuncaoUsuario.class, value);
    }
}