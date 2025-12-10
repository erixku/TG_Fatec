package br.app.harppia.modulo.userconfig.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.custom.enums.InternalEnumParser;
import br.app.harppia.defaults.custom.enums.rules.EnumPersistivel;

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
