package br.app.harppia.modulo.usuario.domain.response;

import java.util.UUID;

public record InformacoesBasicaUsuario(
		UUID id,
		String nome,
		String nomeSocial,
		String email
		
		) {

}
