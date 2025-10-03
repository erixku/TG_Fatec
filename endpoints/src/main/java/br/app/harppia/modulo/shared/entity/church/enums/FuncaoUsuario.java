package br.app.harppia.modulo.shared.entity.church.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum FuncaoUsuario implements EnumPersistivel {
	LIDER("lider"),
	MINISTRO("ministro"),
	LEVITA("levita");

	private String funcao;
	
	FuncaoUsuario(String funcao){
		this.funcao = funcao;
	}

	@Override
	public String getCustomValue() {
		return this.funcao;
	}
}