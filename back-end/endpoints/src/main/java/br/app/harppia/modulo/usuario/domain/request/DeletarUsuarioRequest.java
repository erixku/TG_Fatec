package br.app.harppia.modulo.usuario.domain.request;

import java.util.UUID;

public record DeletarUsuarioRequest(UUID idUsuarioParaApagar, UUID idUsuarioQueApagou) {

}
