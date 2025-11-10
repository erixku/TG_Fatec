package br.app.harppia.modulo.userconfig.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

/**
 * Essa enum representa os tipos de daltonismos existentes (até 02/10/2025).
 * Use o método 'getCustomValue' para retornar a string normalizada equivalente.
 */
public enum ETipoDaltonismo implements EnumPersistivel {

	TRICOMATA("tricromata"),
	PROTANOPIA("protanopia"),
	PROTANOMALIA("protanomalia"),
	DEUTERANOPIA("deuteranopia"),
	DEUTERANOMALIA("deuteranomalia"),
	TRITANOPIA("tritanopia"),
	TRITANOMALIA("tritanomalia"),
	ACROMATOPSIA("acromatopsia");
	
	private String tipoDaltonismo;
	
	ETipoDaltonismo(String tipoDaltonismo){
		this.tipoDaltonismo = tipoDaltonismo;
	}
	
	@JsonValue
	@Override
	public String getCustomValue() {
		return this.tipoDaltonismo;
	}
	
	@JsonCreator
	public ETipoDaltonismo fromValue(String tipoDaltonismo) {
		return InternalEnumParser.fromValue(ETipoDaltonismo.class, tipoDaltonismo);
	}
}
