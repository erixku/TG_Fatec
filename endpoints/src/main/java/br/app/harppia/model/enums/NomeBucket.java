package br.app.harppia.endpoints.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NomeBucket {
	FOTO_PERFIL_USUARIO   ("foto-perfil-usuario"),
	FOTO_PERFIL_IGREJA    ("foto-perfil-igreja"),
	FOTO_PERFIL_MINISTERIO("foto-perfil-ministerio"),
	ICONE_INSTRUMENTO     ("icone-instrumento"),
	AUDIO                 ("audio"),
	PDF                   ("pdf"),
	SISTEMA_ICONE_INSTRUMENTO     ("sistema-icone-instrumento"),
	SISTEMA_IMAGEM        ("sistema-imagem"),
	SISTEMA_AUDIO         ("sistema-audi");
	
	private String nome;
	
	NomeBucket(String nome) {
		this.nome = nome;
	}
	
    @JsonValue
    public String getNome() {
        return nome;
    }

    @JsonCreator
    public static NomeBucket fromNome(String nome) {
        for (NomeBucket bucket : NomeBucket.values()) {
            if (bucket.nome.equalsIgnoreCase(nome)) {
                return bucket;
            }
        }

        throw new IllegalArgumentException("Invalid NomeBucket name: " + nome);
    }
}
