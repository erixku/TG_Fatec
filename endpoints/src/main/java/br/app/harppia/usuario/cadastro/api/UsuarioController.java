package br.app.harppia.usuario.cadastro.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.usuario.cadastro.dtos.UsuarioCadastroDTO;
import br.app.harppia.usuario.cadastro.service.UsuarioCadastroService;
import jakarta.validation.Valid;


/**
 * Responsável por receber todas as requisições relacionadas ao cadastro de 
 * usuários no sistema.
 * 
 * Rota controlada: "users/cadastro"
 * 
 * @author asher_ren
 * @since 15/08/2025
 */
@RestController
@RequestMapping("users/v1/")
public class UsuarioController {

	@Autowired
	private UsuarioCadastroService userCadService;
	
	
	@PostMapping("register/")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO usrCadDTO) {
		userCadService.cadastrarUsuario(usrCadDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
