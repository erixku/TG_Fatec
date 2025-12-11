package br.app.harppia.modulo.church.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.church.infrastructure.repository.enums.ENomeInstrumento;

public class ConversorEnumNomeInstrumento extends ConversorEnumPadrao<ENomeInstrumento> {

	public ConversorEnumNomeInstrumento() {
		super(ENomeInstrumento.class);
	}
}
