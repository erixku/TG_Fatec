package br.app.harppia.modulo.auth.domain.request;

public record ConfirmarEmailRequest(String email, String senha, String codigo) {
}
