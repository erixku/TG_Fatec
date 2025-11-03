package br.app.harppia.modulo.usuario.domain.dto.login;

import java.util.UUID;

public record InformacoesLoginUsuarioBanco(UUID id, String nome, String cpf, String email, String telefone,
		String senha) {

}
