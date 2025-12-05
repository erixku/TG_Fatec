package br.app.harppia.modulo.church.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum EFamiliaInstrumento implements EnumPersistivel {
	CORDAS("cordas");

	private String familia;

	private EFamiliaInstrumento(String familia) {
		this.familia = familia;
	}
	
	@JsonValue
	@Override
	public String getCustomValue() {
		return this.familia;
	}
	
    @JsonCreator
    public static EFamiliaInstrumento fromValue(String value) {
    	return InternalEnumParser.fromValue(EFamiliaInstrumento.class, value);
    }
}