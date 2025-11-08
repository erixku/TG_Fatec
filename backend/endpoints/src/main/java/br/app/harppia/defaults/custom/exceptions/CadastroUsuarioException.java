package br.app.harppia.defaults.custom.exceptions;

import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.hibernate.tool.schema.spi.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.app.harppia.configs.WarningController;

public class CadastroUsuarioException extends RuntimeException implements ExceptionHandler {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(WarningController.class);
	
	public CadastroUsuarioException() {}
	
	public CadastroUsuarioException(String message) {
		super(message);
	}

	@Override
	public void handleException(CommandAcceptanceException exception) {
	    log.error("Erro ao aceitar comando: {}", exception.getMessage(), exception);
	}

}
