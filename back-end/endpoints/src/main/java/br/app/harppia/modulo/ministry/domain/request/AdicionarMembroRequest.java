package br.app.harppia.modulo.ministry.domain.request;

import java.util.UUID;

import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;

public record AdicionarMembroRequest(UUID idCriador, EFuncaoMembro funcao, UUID idMinisterio, UUID idMembro) {
}
