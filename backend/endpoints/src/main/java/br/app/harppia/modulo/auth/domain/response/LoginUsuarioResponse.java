package br.app.harppia.modulo.auth.domain.response;

import java.util.UUID;

public record LoginUsuarioResponse(UUID id, String email, String name) {

}
