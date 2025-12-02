package br.app.harppia.modulo.usuario.infrasctructure.repository.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum EStatusUsuario implements EnumPersistivel {
	
	EM_VERIFICACAO,
	ATIVO,
	DELETADO,
	;

	@Override
	public String getCustomValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
