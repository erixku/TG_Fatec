package br.app.harppia.modulo.music.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum ETonalidadeMusica implements EnumPersistivel {
    // Tonalidades maiores
    C("C"), C_SHARP("C#"),
    D("D"), D_SHARP("D#"),
    E("E"),
    F("F"), F_SHARP("F#"),
    G("G"), G_SHARP("G#"),
    A("A"), A_SHARP("A#"),
    B("B"),

    // Tonalidades menores
    AM("Am"), A_SHARP_M("A#m"),
    BM("Bm"),
    CM("Cm"), C_SHARP_M("C#m"),
    DM("Dm"), D_SHARP_M("D#m"),
    EM("Em"),
    FM("Fm"), F_SHARP_M("F#m"),
    GM("Gm"), G_SHARP_M("G#m");

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
