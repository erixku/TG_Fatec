package br.app.harppia.modulo.file.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumNomeBucket extends ConversorEnumPadrao<ENomeBucket> {

	public ConversorEnumNomeBucket() {
		super(ENomeBucket.class);
	}

}
