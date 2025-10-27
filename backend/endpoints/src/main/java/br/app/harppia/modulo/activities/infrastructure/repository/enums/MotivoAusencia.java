package br.app.harppia.modulo.activities.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum MotivoAusencia implements EnumPersistivel {
	TRABALHO("trabalho"),
	LICENCA_MEDICA("licença médica"),
	EXAME_LABORATORIAL("exame laboratorial"),
	OUTROS("outros");
	
	private String motivo;
	
	private MotivoAusencia(String motivo) {
		this.motivo = motivo;
	}

	@JsonValue()
	@Override
	public String getCustomValue() {
		return this.motivo;
	}
	
	@JsonCreator
	public MotivoAusencia fromValue(String value) {
		return InternalEnumParser.fromValue(MotivoAusencia.class, value);
	}
}
