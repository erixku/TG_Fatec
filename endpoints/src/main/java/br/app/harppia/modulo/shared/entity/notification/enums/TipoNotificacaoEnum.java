package br.app.harppia.modulo.shared.entity.notification.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum TipoNotificacaoEnum implements EnumPersistivel {
    NOVO_AVISO("novo aviso"),
    NOVA_ESCALA("nova escala"),
    ATRIBUICAO_DE_ESCALA("atribuição de escala"),
    NOVO_AGENDAMENTO("novo agendamento"),
    ATRIBUICAO_DE_AGENDAMENTO("atribuição de agendamento"),
    NOVO_COMPROMISSO("novo compromisso"),
    LEMBRETE_DE_ANIVERSARIO("lembrete de aniversário"),
    MENCAO_EM_MENSAGEM("menção em mensagem");

    private final String tipoNotif;

    TipoNotificacaoEnum(String value) {
        this.tipoNotif = value;
    }

    @JsonValue
    @Override	
    public String getCustomValue() {
        return tipoNotif;
    }
    
    @JsonCreator
    public static TipoNotificacaoEnum fromValue(String str) {
    	return InternalEnumParser.fromValue(TipoNotificacaoEnum.class, str);
    }
}
