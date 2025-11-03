package br.app.harppia.modulo.auth.domain.request;

public record LoginUsuarioRequest(String telefone, String cpf, String email, String senha) {
}
