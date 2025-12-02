package br.app.harppia.modulo.ministry.domain.request;

import java.util.UUID;

public record AdicionarMembroRequest(UUID idCriador, String funcao, UUID idMinisterio, UUID idMembro) {
}
