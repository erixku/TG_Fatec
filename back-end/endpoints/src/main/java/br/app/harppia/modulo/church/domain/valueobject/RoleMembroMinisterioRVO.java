package br.app.harppia.modulo.church.domain.valueobject;

import java.io.Serializable;
import java.util.UUID;

import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;

public record RoleMembroMinisterioRVO(UUID idMinisterio, EFuncaoMembro funcao) implements Serializable {

}