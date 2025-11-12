package br.app.harppia.modulo.usuario.domain.valueobject;

import java.util.UUID;

public interface BuscarInformacoesPublicasIVO {
	UUID setId();
	UUID getId();
	String setNome();
	String getNome();
	String setNomeSocial();
	String getNomeSocial();
	String setEmail();
	String getEmail();
	UUID setIdFotoPerfil();
	UUID getIdFotoPerfil();
}
