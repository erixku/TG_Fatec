package br.app.harppia.modulo.usuario.domain.dto.register;

import java.util.UUID;

public record UsuarioCadastradoDTO(UUID uuid, String email, String nome) {
	
}
