package br.app.harppia.modulo.usuario.domain.valueobject;

import java.util.UUID;

public interface ValidacaoUsuarioExistenteVO {
	UUID setId();
	UUID getId();
	String setCpf();
	String getCpf();
	String setEmail();
	String getEmail();	
	String setTelefone();
	String getTelefone();	
}
