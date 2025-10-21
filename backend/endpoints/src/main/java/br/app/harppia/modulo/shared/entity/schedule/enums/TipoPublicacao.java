package br.app.harppia.modulo.shared.entity.schedule.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum TipoPublicacao implements EnumPersistivel {
	AVISO("aviso"),
	AGENDAMENTO("agendamento"),
	COMPROMISSO("compromisso");
	
	private String tipo;
	
	private TipoPublicacao(String tipo) {
		this.tipo = tipo;
	}
	
	@JsonValue
	@Override
	public String getCustomValue() {
		return this.tipo;
	}
	
	@JsonCreator
	public TipoPublicacao fromValue(String value) {
		return InternalEnumParser.fromValue(TipoPublicacao.class, value);
	}
}
