package br.app.harppia.modulo.usuario.domain.valueobject;

import java.util.UUID;

public interface BuscarInformacoesAutenticacaoIVO {
	UUID getId();
	String getEmail();
	String getSenha();	
}
