package br.app.harppia.modulo.file.infrastructure.repository.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.custom.enums.InternalEnumParser;
import br.app.harppia.defaults.custom.enums.rules.ContentExistsValidation;
import br.app.harppia.defaults.custom.enums.rules.EnumPersistivel;

/**
 * Representa o tipo nominal do arquivo.
 */
public enum EExtensaoArquivo implements EnumPersistivel, ContentExistsValidation<EExtensaoArquivo> {
	
	// IMAGENS
	PNG("png"), JPG("jpg"), JPEG("jpeg"), SVG("svg"), 
	
	// AUDIO
	MP3("mp3"), WAV("wav"), OGG("ogg"), FLAC("flac"), M4A("m4a"),
	
	// OUTROS
	ALAC("alac"), PDF("pdf");

	private String extensao;

	private EExtensaoArquivo(String extensao) {
		this.extensao = extensao;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return this.extensao;
	}

	@JsonCreator
	public static EExtensaoArquivo fromValue(String value) {
		return InternalEnumParser.fromValue(EExtensaoArquivo.class, value);
	}
	
	public static boolean contains(String value) {
		return ContentExistsValidation.contains(EExtensaoArquivo.class, value);
	}
}
