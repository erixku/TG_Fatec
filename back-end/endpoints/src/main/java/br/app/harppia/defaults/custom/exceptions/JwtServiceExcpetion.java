package br.app.harppia.defaults.custom.exceptions;

public class JwtServiceExcpetion extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public JwtServiceExcpetion(String message) {
		super(message);
	}
}
