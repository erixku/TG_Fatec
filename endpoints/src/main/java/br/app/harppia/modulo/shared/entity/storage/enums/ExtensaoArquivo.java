package br.app.harppia.modulo.shared.entity.storage.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

/**
 * Representa o tipo nominal do arquivo.
 */
public enum ExtensaoArquivo implements EnumPersistivel {
	PNG("png"), JPG("jpg"), JPEG("jpeg"), SVG("svg"), 
	MP3("mp3"), WAV("wav"), OGG("ogg"), FLAC("flac"), M4A("m4a"),
	ALAC("alac"), PDF("pdf");

	private String extensao;

	private ExtensaoArquivo(String extensao) {
		this.extensao = extensao;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.extensao;
	}

	@JsonCreator
	public static ExtensaoArquivo fromValue(String value) {
		return InternalEnumParser.fromValue(ExtensaoArquivo.class, value);
	}
}
