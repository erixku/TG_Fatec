package br.app.harppia.defaults.custom.converters.enums.mimetypearquivo;



import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.defaults.shared.enums.MimeTypeArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumMimeTypeArquivo extends ConversorEnumPadrao<MimeTypeArquivo>{

	public ConversorEnumMimeTypeArquivo() {
		super(MimeTypeArquivo.class);
	}

}
