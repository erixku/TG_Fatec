package br.app.harppia.modulo.usuario.cadastro.dto;

import java.util.UUID;

public record UsuarioCadastradoDTO(UUID uuid, String email, String nome) {
	
}
