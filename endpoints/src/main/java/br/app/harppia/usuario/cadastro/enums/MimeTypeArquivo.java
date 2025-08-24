package br.app.harppia.usuario.cadastro.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.usuario.cadastro.utils.rules.EnumPersistivel;

public enum MimeTypeArquivo implements EnumPersistivel {
	IMAGE_PNG           ("image/png"),
	IMAGE_JPEG          ("image/jpeg"),
	IMAGE_SVG_PLUS_XML  ("image/svg+xml"),
	AUDIO_MPEG          ("audio/mpeg"),
	AUDIO_WAV           ("audio/wav'"),
	AUDIO_OGG           ("audio/ogg"),
	AUDIO_FLAC          ("audio/flac"),
	AUDIO_MP4           ("audio/mp4"),
	AUDIO_X_ALAC        ("audio/x-alac"),
	APPLICATION_PDF     ("application/pdf");

	private String mimeType;
	
	private MimeTypeArquivo(String mimeType) {
		this.mimeType = mimeType;
	}
	
    @JsonValue
    public String getMimeType() {
        return mimeType;
    }

    @JsonCreator
    public static MimeTypeArquivo fromValue(String value) {
        for (MimeTypeArquivo tipo : MimeTypeArquivo.values()) {
            if (tipo.mimeType.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo MIME inválido: " + value);
    }

	@Override
	public String getValorCustomizado() {
		return this.mimeType;
	}
}
