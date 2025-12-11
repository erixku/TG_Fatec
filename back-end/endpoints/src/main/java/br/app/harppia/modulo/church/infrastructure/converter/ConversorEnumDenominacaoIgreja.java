
package br.app.harppia.modulo.church.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.church.infrastructure.repository.enums.EDenominacaoIgreja;

public class ConversorEnumDenominacaoIgreja extends ConversorEnumPadrao<EDenominacaoIgreja> {

	public ConversorEnumDenominacaoIgreja() {
		super(EDenominacaoIgreja.class);
	}

}
