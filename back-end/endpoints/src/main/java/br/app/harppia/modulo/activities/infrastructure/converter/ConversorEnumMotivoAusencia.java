package br.app.harppia.modulo.activities.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.activities.infrastructure.repository.enums.ETipoPublicacao;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumMotivoAusencia extends ConversorEnumPadrao<ETipoPublicacao> {

	public ConversorEnumMotivoAusencia() {
		super(ETipoPublicacao.class);
	}
}
