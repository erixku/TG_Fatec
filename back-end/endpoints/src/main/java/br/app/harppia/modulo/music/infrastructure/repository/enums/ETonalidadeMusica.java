package br.app.harppia.modulo.music.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.custom.enums.rules.EnumPersistivel;

public enum ETonalidadeMusica implements EnumPersistivel {
	// Tonalidades maiores
	C("c"), C_SHARP("c#"),
	D("d"), D_SHARP("d#"),
	E("e"),
	F("f"), F_SHARP("f#"),
	G("g"), G_SHARP("g#"),
	A("a"), A_SHARP("a#"),
	B("b"),

	// Tonalidades menores
	AM("am"), A_SHARP_M("a#m"),
	BM("bm"),
	CM("cm"), C_SHARP_M("c#m"),
	DM("dm"), D_SHARP_M("d#m"),
	EM("em"),
	FM("fm"), F_SHARP_M("f#m"),
	GM("gm"), G_SHARP_M("g#m");

    private final String value;

    ETonalidadeMusica(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String getCustomValue() {
        return value;
    }

    @JsonCreator
    public static ETonalidadeMusica fromValue(String value) {
        for (ETonalidadeMusica t : ETonalidadeMusica.values()) {
            if (t.value.equalsIgnoreCase(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tonalidade inválida: " + value);
    }
}
