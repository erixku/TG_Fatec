package br.app.harppia.modulo.shared.entity.church.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum TipoAtividade implements EnumPersistivel{
	AGENDAMENTO("agendamento"),
	COMPROMISSO("compromisso");
	
	private String tipoAtividade;
	
	TipoAtividade(String tipoAtividade){
		this.tipoAtividade = tipoAtividade;
	}

	@Override
	public String getCustomValue() {
		return this.tipoAtividade;
	}
}
