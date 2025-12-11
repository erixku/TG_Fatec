package br.app.harppia.modulo.usuario.domain.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AtualizarUsuarioDTO(
		
		// PK
		UUID idUsuario,
		
		// dados de log
		OffsetDateTime updatedAt, 
		
		// dados do usuário
		String nomeCompleto, String nomeSocialCompleto,
		LocalDate dataNascimento, String telefone, String senha,
		
		// FKs
		UUID idFotoPerfil
	) {
}