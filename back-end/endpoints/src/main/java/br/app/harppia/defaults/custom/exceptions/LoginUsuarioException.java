package br.app.harppia.defaults.custom.exceptions;

public class LoginUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginUsuarioException() {}
	
	public LoginUsuarioException(String message) {
		super(message);
	}
	
}
