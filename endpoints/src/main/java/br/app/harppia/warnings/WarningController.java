package br.app.harppia.warnings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WarningController {

	// Primeiro diz qual expection vai tratar - tem q ser a mesma na annotation e no parâmetro
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> logError(Exception ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Houve um erro ao carregar a página...\n\n" + ex.getMessage());
	}
}
