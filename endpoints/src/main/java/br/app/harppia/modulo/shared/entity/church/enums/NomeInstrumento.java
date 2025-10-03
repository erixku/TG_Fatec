package br.app.harppia.modulo.shared.entity.church.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum NomeInstrumento implements EnumPersistivel {
	OUTRO("outro"),
	VIOLAO("violao");
	
	private String nome;
	
	NomeInstrumento(String nome) {
		this.nome = nome;
	}

	@Override
	public String getCustomValue() {
		return this.nome;
	}
}