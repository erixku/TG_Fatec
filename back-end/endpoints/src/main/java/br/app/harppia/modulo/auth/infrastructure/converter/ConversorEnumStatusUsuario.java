package br.app.harppia.modulo.auth.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.usuario.infrasctructure.repository.enums.EStatusUsuario;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumStatusUsuario extends ConversorEnumPadrao<EStatusUsuario> {

	public ConversorEnumStatusUsuario() {
		super(EStatusUsuario.class);
	}

}
