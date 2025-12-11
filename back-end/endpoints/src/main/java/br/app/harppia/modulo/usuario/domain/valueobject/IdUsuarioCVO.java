package br.app.harppia.modulo.usuario.domain.valueobject;

import java.util.UUID;

public class IdUsuarioCVO {
	private UUID id;

	public IdUsuarioCVO(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
