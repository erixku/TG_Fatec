package br.app.harppia.modulo.shared.entity.accessibility.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum ECorTema implements EnumPersistivel {
	ESCURO("escuro"), CLARO("claro");

	private String cor;

	ECorTema(String cor) {
		this.cor = cor;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.cor;
	}
	
	@JsonCreator
	public ECorTema fromValue(String tema) {
		return InternalEnumParser.fromValue(ECorTema.class, tema);
	}
}
