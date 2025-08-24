package br.app.harppia.usuario.cadastro.converters.enums.extensaoarquivo;


import br.app.harppia.usuario.cadastro.converters.ConversorEnumPadrao;
import br.app.harppia.usuario.cadastro.enums.ExtensaoArquivo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumExtensaoArquivo extends ConversorEnumPadrao<ExtensaoArquivo> {

	public ConversorEnumExtensaoArquivo() {
		super(ExtensaoArquivo.class);
	}
}
