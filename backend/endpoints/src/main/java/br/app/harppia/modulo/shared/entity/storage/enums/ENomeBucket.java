package br.app.harppia.modulo.shared.entity.storage.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.app.harppia.defaults.utils.InternalEnumParser;
import br.app.harppia.defaults.utils.rules.EnumPersistivel;

/**
 * Representa o agrupamento de registros do banco de dados. 
 * Cada bucket agrupa um tipo de dado.
 */
public enum ENomeBucket implements EnumPersistivel {
	FOTO_PERFIL_USUARIO("foto-perfil-usuario"), 
	FOTO_PERFIL_IGREJA("foto-perfil-igreja"),
	FOTO_PERFIL_MINISTERIO("foto-perfil-ministerio"),
	ICONE_INSTRUMENTO("icone-instrumento"),
	AUDIO("audio"),
	PDF("pdf"),
	SISTEMA_ICONE_INSTRUMENTO("sistema-icone-instrumento"), 
	SISTEMA_IMAGEM("sistema-imagem"),
	SISTEMA_AUDIO("sistema-audio");

	private String nome;

	ENomeBucket(String nome) {
		this.nome = nome;
	}

	@JsonValue
	@Override
	public String getCustomValue() {
		return nome;
	}

	@JsonCreator
	public static ENomeBucket fromNome(String nome) {
		return InternalEnumParser.fromValue(ENomeBucket.class, nome);
	}
}
