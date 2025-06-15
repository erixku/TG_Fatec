package br.app.harppia.controllers.test;


import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/test/")
public class TesteConexaoBanco {
	@Autowired
	private DataSource dataSource;
	
	@GetMapping("/conexao-banco")
	public ResponseEntity<String> isConnectingDatabase(){
		
		try {
			Connection a = dataSource.getConnection();
			
			return ResponseEntity.ok((a.isValid(10)) ? "<h1>deu bom</h1>" : "não deu bom");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok("Saiu do catch");
		
	}
}
