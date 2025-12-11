package br.app.harppia.modulo.home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class RootController {

	@GetMapping
	public ResponseEntity<String> helloWorld(){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("A API está online!");
	}
	
}
