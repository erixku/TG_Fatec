package br.app.harppia.defaults.custom.converters.enums.mimetypearquivo;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumMimeTypeArquivo extends ConversorEnumPadrao<EMimeTypeArquivo> {

	public ConversorEnumMimeTypeArquivo() {
		super(EMimeTypeArquivo.class);
	}
}
