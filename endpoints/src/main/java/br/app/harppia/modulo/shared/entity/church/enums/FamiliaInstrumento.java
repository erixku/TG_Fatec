package br.app.harppia.modulo.shared.entity.church.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

/**
 * Indica a qual família um instrumento pertence.
 */
public enum FamiliaInstrumento implements EnumPersistivel {
	CORDAS("cordas");

	private String familia;

	private FamiliaInstrumento(String familia) {
		this.familia = familia;
	}

	/**
	 * Retorna o nome personalizado da constante. Exemplo:
	 * 
	 * Para a constante FamiliaInstrumento.CORDAS, o valor retornado é `"cordas"`
	 */
	@Override
	public String getCustomValue() {
		return this.familia;
	}
}