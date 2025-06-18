package br.app.harppia.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.app.utils.rules.EnumPersistivel;

public enum ExtensaoArquivo implements EnumPersistivel {
	PNG     ("png"),
	JPG     ("jpg"),
	JPEG    ("jpeg"),
	SVG     ("svg"),
	MP3     ("mp3"),
	WAV     ("wav"),
	OGG     ("ogg"),
	FLAC    ("flac"),
	M4A     ("m4a"),
	ALAC    ("alac"),
	PDF     ("pdf");
	
	private String extensao;
	
	private ExtensaoArquivo(String extensao) {
		this.extensao = extensao;
	}
	
    @JsonValue
    public String getExtension() {
        return extensao;
    }

    @JsonCreator
    public static ExtensaoArquivo fromValue(String value) {
        for (ExtensaoArquivo tipo : ExtensaoArquivo.values()) {
            if (tipo.extensao.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Extensão inválida: " + value);
    }

	@Override
	public String getValorCustomizado() {
		return this.extensao;
	}
}
