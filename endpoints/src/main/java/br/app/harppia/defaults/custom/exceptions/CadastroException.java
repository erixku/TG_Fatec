package br.app.harppia.defaults.custom.exceptions;

import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.hibernate.tool.schema.spi.ExceptionHandler;

public class CadastroException extends Exception implements ExceptionHandler{

	private static final long serialVersionUID = 1L;

	@Override
	public void handleException(CommandAcceptanceException exception) {
		// TODO Auto-generated method stub
		
	}

}
