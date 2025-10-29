package br.app.harppia.modulo.usuario.domain.dto;

import java.util.UUID;

public record InformacaoPublicaUsuarioDTO(String cpf, String nome, String nomeSocial, String email,
		UUID idFotoPerfil) {
}