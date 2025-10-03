package br.app.harppia.modulo.shared.entity.storage.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

/**
 * Representa o tipo real do arquivo (mime type).
 */
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
	@Override
	public String getCustomValue() {
		return this.mimeType;
	}
	
    @JsonCreator
    public static MimeTypeArquivo fromValue(String value) {
        return InternalEnumParser.fromValue(MimeTypeArquivo.class, value);
    }
}
