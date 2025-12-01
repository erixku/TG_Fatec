package br.app.harppia.modulo.usuario.domain.dto.login;

import java.util.UUID;

public record InformacoesLoginUsuarioBanco(UUID id, String email, String nome, String senha) {

}
