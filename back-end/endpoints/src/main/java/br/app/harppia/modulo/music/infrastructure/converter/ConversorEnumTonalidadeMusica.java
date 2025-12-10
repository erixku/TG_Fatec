package br.app.harppia.modulo.music.infrastructure.converter;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.music.infrastructure.repository.enums.ETonalidadeMusica;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumTonalidadeMusica extends ConversorEnumPadrao<ETonalidadeMusica> {

	public ConversorEnumTonalidadeMusica() {
		super(ETonalidadeMusica.class);
	}
}
