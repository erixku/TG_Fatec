package br.app.harppia.modulo.ministry.domain.response;

import java.util.UUID;

import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;

public record AdicionarMembroResponse(UUID idMembro, EFuncaoMembro funcao) {

}
