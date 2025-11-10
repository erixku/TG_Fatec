package br.app.harppia.modulo.auth.domain.login.request;

public record LoginUsuarioRequest(String telefone, String cpf, String email, String senha) {
}
