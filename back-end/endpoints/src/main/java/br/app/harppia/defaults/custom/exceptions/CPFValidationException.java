package br.app.harppia.defaults.custom.exceptions;

public class CPFValidationException extends RuntimeException  {
	
	private static final long serialVersionUID = 1L;

	public CPFValidationException(String message) {
		super(message);
	}
}
