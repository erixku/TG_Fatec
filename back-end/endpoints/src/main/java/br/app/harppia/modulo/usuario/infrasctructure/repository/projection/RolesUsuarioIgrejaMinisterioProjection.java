package br.app.harppia.modulo.usuario.infrasctructure.repository.projection;

import java.util.UUID;

public interface RolesUsuarioIgrejaMinisterioProjection {
	UUID getIgreja();
	UUID getMinisterio();
	Short getFuncao();
	String getRoleUsuarioIgreja();
}
