package br.app.harppia.modulo.music.domain.request;

import java.util.UUID;

public record CadastroMusicaRequest(UUID idUsuarioCriador, String nome, String artista, Boolean temArtistaSecundario,
		String album, String duracaoMusica, Short bpm, String tonalidade, String linkMusica, String linkLetra,
		String linkCifra, String linkPartitura, Boolean parteDeMedley, UUID idCapa) {
}
