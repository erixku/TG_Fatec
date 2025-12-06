package br.app.harppia.modulo.church.infrastructure.repository.projection;

import java.util.UUID;

public interface AllRolesMembroIVO {
	UUID getIgreja();
	UUID getMinisterio();
	String getFuncao(); 
	String getRoleUsuarioIgreja();
}
