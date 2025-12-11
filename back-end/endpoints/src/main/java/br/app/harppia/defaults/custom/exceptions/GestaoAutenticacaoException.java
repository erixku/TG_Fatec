package br.app.harppia.defaults.custom.exceptions;

public class GestaoAutenticacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GestaoAutenticacaoException() {}
	
	public GestaoAutenticacaoException(String message) {
		super(message);
	}
	
}
