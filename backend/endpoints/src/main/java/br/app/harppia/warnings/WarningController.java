package br.app.harppia.warnings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.app.harppia.defaults.custom.exceptions.CPFValidationException;
import br.app.harppia.defaults.custom.exceptions.CadastroUsuarioException;
import br.app.harppia.defaults.custom.exceptions.RegistrarArquivoException;

@RestControllerAdvice
public class WarningController {

	// Primeiro diz qual expection vai tratar - tem q ser a mesma na annotation e no parâmetro
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> logGenericError(Exception ex){
		System.err.println("- - - - - - ERRO INTERNO - - - - - -");
		System.err.println("Causa do erro: " + ex.getCause());
		System.err.println("Origem do erro: " + ex.getStackTrace());
		System.err.println("Contexto do erro: " + ex.getLocalizedMessage());
		ex.printStackTrace();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Não é você... Sou eu :( \n");
		
	}
	
	@ExceptionHandler(CPFValidationException.class)
	public ResponseEntity<String> logCPFValidationError(CPFValidationException ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Houve um erro ao validar o CPF...\n\n" + ex.getCause());
	}
	
	@ExceptionHandler(CadastroUsuarioException.class)
	public ResponseEntity<String> logError(CadastroUsuarioException ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Houve um erro ao realizar o cadastro...\n" + ex.getCause());
	}

	@ExceptionHandler(RegistrarArquivoException.class)
	public ResponseEntity<String> logError(RegistrarArquivoException ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Não foi possível salvar o arquivo. Tente novamente mais tarde.\n" + ex.getCause());
	}
}
