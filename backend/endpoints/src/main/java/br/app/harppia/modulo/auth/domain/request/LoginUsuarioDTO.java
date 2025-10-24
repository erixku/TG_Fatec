package br.app.harppia.modulo.auth.domain.request;

public record LoginUsuarioDTO(String telefone, String cpf, String email, String senha) {
}
