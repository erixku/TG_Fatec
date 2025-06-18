package br.app.harppia.app.utils.converters.enums.nomebucket;

import br.app.harppia.app.utils.converters.ConversorEnumPadrao;
import br.app.harppia.model.enums.NomeBucket;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumNomeBucket extends ConversorEnumPadrao<NomeBucket> {

	public ConversorEnumNomeBucket() {
		super(NomeBucket.class);
	}

}
