package br.app.harppia.modulo.activities.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.custom.enums.InternalEnumParser;
import br.app.harppia.defaults.custom.enums.rules.EnumPersistivel;

public enum ETipoPublicacao implements EnumPersistivel {
	AVISO("aviso"),
	AGENDAMENTO("agendamento"),
	COMPROMISSO("compromisso");
	
	private String tipo;
	
	private ETipoPublicacao(String tipo) {
		this.tipo = tipo;
	}
	
	@JsonValue
	@Override
	public String getCustomValue() {
		return this.tipo;
	}
	
	@JsonCreator
	public static ETipoPublicacao fromString(String value) {
		return InternalEnumParser.fromValue(ETipoPublicacao.class, value);
	}
}