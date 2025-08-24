package br.app.harppia.usuario.cadastro.converters.enums.nomebucket;

import br.app.harppia.usuario.cadastro.converters.ConversorEnumPadrao;
import br.app.harppia.usuario.cadastro.enums.NomeBucket;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumNomeBucket extends ConversorEnumPadrao<NomeBucket> {

	public ConversorEnumNomeBucket() {
		super(NomeBucket.class);
	}

}
