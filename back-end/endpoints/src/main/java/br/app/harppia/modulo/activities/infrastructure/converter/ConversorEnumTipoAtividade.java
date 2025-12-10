package br.app.harppia.modulo.activities.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.church.infrastructure.repository.enums.ETipoAtividade;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumTipoAtividade extends ConversorEnumPadrao<ETipoAtividade> {

	public ConversorEnumTipoAtividade() {
		super(ETipoAtividade.class);
	}
}
