package br.app.harppia.defaults.custom.converters.enums.nomebucket;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.defaults.shared.enums.NomeBucket;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumNomeBucket extends ConversorEnumPadrao<NomeBucket> {

	public ConversorEnumNomeBucket() {
		super(NomeBucket.class);
	}

}
