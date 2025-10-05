package br.app.harppia.modulo.shared.entity.church.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum ETipoAtividade implements EnumPersistivel{
	AGENDAMENTO("agendamento"),
	COMPROMISSO("compromisso");
	
	private String tipoAtividade;
	
	ETipoAtividade(String tipoAtividade){
		this.tipoAtividade = tipoAtividade;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.tipoAtividade;
	}
	
    @JsonCreator
    public static ETipoAtividade fromValue(String value) {
    	return InternalEnumParser.fromValue(ETipoAtividade.class, value);
    }
}
