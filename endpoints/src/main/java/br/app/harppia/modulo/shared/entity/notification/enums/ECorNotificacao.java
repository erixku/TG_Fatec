package br.app.harppia.modulo.shared.entity.notification.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum ECorNotificacao implements EnumPersistivel{
	AZUL("azul");

    private final String value;

    ECorNotificacao(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getCustomValue() {
        return value;
    }

    @JsonCreator
    public static ECorNotificacao fromValue(String value) {
        return InternalEnumParser.fromValue(ECorNotificacao.class, value);
    }
}
