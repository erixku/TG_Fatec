package br.app.harppia.defaults.custom.converters.enums.extensaoarquivo;


import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.defaults.shared.enums.ExtensaoArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumExtensaoArquivo extends ConversorEnumPadrao<ExtensaoArquivo> {

	public ConversorEnumExtensaoArquivo() {
		super(ExtensaoArquivo.class);
	}
}
