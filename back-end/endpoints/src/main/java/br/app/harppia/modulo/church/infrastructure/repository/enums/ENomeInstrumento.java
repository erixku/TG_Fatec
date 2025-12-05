package br.app.harppia.modulo.church.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum ENomeInstrumento implements EnumPersistivel {
	OUTRO("outro"),
	VIOLAO("violao");
	
	private String nome;
	
	ENomeInstrumento(String nome) {
		this.nome = nome;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.nome;
	}
	
    @JsonCreator
    public static ENomeInstrumento fromValue(String value) {
    	return InternalEnumParser.fromValue(ENomeInstrumento.class, value);
    }
}