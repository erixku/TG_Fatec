package br.app.harppia.modulo.usuario.domain.valueobject;

import java.util.UUID;

public interface BuscarInformacoesAutenticacaoIVO {
	UUID setId();
	UUID getId();
	String setNome();
	String getNome();
	String setEmail();
	String getEmail();
	String setSenha();
	String getSenha();	
}
