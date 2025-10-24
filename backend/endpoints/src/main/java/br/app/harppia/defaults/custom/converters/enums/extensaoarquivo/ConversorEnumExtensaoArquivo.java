package br.app.harppia.defaults.custom.converters.enums.extensaoarquivo;


import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumExtensaoArquivo extends ConversorEnumPadrao<EExtensaoArquivo> {

	public ConversorEnumExtensaoArquivo() {
		super(EExtensaoArquivo.class);
	}
}
