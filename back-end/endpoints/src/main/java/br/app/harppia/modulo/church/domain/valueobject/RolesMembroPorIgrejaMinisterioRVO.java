package br.app.harppia.modulo.church.domain.valueobject;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record RolesMembroPorIgrejaMinisterioRVO(
		UUID idIgreja,
		List<String> cargosNaIgreja,
		List<RoleMembroMinisterioRVO> funcaoPorMinisterio
	) implements Serializable {
}