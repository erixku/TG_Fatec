package br.app.harppia.app.utils.converters.enums.mimetypearquivo;

import br.app.harppia.app.utils.converters.ConversorEnumPadrao;
import br.app.harppia.model.enums.MimeTypeArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumMimeTypeArquivo extends ConversorEnumPadrao<MimeTypeArquivo>{

	public ConversorEnumMimeTypeArquivo() {
		super(MimeTypeArquivo.class);
	}

}
