package br.app.harppia.modulo.usuario.domain.dto;

import java.util.UUID;

public record InformacaoPublicaUsuarioDTO(UUID id, String nome, String nomeSocial, String email, UUID idFotoPerfil) {
}