package br.app.harppia.modulo.shared.entity.accessibility.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum CorTema implements EnumPersistivel {
	ESCURO("escuro"), CLARO("claro");

	private String cor;

	CorTema(String cor) {
		this.cor = cor;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.cor;
	}
	
	@JsonCreator
	public CorTema fromValue(String tema) {
		return InternalEnumParser.fromValue(CorTema.class, tema);
	}
}
