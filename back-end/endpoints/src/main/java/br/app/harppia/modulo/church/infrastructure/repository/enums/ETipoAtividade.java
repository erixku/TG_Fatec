package br.app.harppia.modulo.church.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.custom.enums.InternalEnumParser;
import br.app.harppia.defaults.custom.enums.rules.EnumPersistivel;

public enum ETipoAtividade implements EnumPersistivel {
	AGENDAMENTO("agendamento"), 
	COMPROMISSO("compromisso"), 
	CLASSIFICACAO_MUSICA("classificação de música");

	private String tipoAtividade;

	ETipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.tipoAtividade;
	}

	@JsonCreator
	public static ETipoAtividade fromString(String value) {
		return InternalEnumParser.fromValue(ETipoAtividade.class, value);
	}
}
