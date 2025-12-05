package br.app.harppia.modulo.usuario.infrasctructure.repository.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum EStatusUsuario implements EnumPersistivel {
	
	EM_VERIFICACAO("em_verificacao"),
	ATIVO("ativo"),
	DELETADO("deletado");
	
	private String status;
	
	private EStatusUsuario(String valor) {
		this.status = valor;
	}

	@Override
	public String getCustomValue() {
		return this.status;
	}

}
