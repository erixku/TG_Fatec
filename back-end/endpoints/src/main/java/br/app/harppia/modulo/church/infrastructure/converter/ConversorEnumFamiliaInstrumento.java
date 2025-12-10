package br.app.harppia.modulo.church.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.church.infrastructure.repository.enums.EFamiliaInstrumento;

public class ConversorEnumFamiliaInstrumento extends ConversorEnumPadrao<EFamiliaInstrumento> {

	public ConversorEnumFamiliaInstrumento() {
		super(EFamiliaInstrumento.class);
	}
}
