package br.app.harppia.modulo.ministry.infraestructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum EFuncaoMembro implements EnumPersistivel {
	LIDER("lider"),
	MINISTRO("ministro"),
	LEVITA("levita");

	private String funcao;
	
	EFuncaoMembro(String funcao){
		this.funcao = funcao;
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
}