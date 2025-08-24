package br.app.harppia.usuario.cadastro.converters.enums.mimetypearquivo;

import br.app.harppia.usuario.cadastro.converters.ConversorEnumPadrao;
import br.app.harppia.usuario.cadastro.enums.MimeTypeArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumMimeTypeArquivo extends ConversorEnumPadrao<MimeTypeArquivo>{

	public ConversorEnumMimeTypeArquivo() {
		super(MimeTypeArquivo.class);
	}

}
