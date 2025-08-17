package br.app.harppia.app.utils.converters.enums.extensaoarquivo;

import br.app.harppia.app.utils.converters.ConversorEnumPadrao;
import br.app.harppia.model.enums.ExtensaoArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumExtensaoArquivo extends ConversorEnumPadrao<ExtensaoArquivo> {

	public ConversorEnumExtensaoArquivo() {
		super(ExtensaoArquivo.class);
	}
}
