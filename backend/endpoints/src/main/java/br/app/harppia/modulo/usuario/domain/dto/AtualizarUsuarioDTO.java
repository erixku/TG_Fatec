package br.app.harppia.modulo.usuario.domain.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AtualizarUsuarioDTO(
		
		// PK
		UUID uuidUsuario,
		
		// dados de log
		OffsetDateTime updatedAt, 
		
		// dados do usuário
		String nomeCompleto, String nomeSocialCompleto,
		OffsetDateTime dataNascimento, String telefone, String senha,
		
		// FKs
		UUID uuidFotoPerfil
	) {
}