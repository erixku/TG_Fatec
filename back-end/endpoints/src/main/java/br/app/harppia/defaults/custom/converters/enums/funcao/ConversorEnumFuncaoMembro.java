package br.app.harppia.defaults.custom.converters.enums.funcao;

import br.app.harppia.defaults.custom.converters.ConversorEnumPadrao;
import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorEnumFuncaoMembro extends ConversorEnumPadrao<EFuncaoMembro> {

	public ConversorEnumFuncaoMembro() {
		super(EFuncaoMembro.class);
	}

}
