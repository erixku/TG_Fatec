package br.app.harppia.modulo.usuario.cadastro.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.usuario.cadastro.dto.UsuarioCadastradoDTO;
import br.app.harppia.modulo.usuario.cadastro.dto.UsuarioCadastroDTO;
import br.app.harppia.modulo.usuario.cadastro.service.UsuarioCadastroService;
import jakarta.validation.Valid;

/**
 * Responsável por receber todas as requisições relacionadas ao cadastro de 
 * usuários no sistema.
 * 
 * Rota controlada: "/users/v1"
 * 
 * @author asher_ren
 * @since 15/08/2025
 */
@RestController
@RequestMapping("/v1/users")
public class UsuarioController {

	@Autowired
	private UsuarioCadastroService userCadService;
	
	@PostMapping("/register")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO usrCadDTO) {
		
		UsuarioCadastradoDTO userCadastrado = userCadService.cadastrarUsuario(usrCadDTO);
		
		if(userCadastrado != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(userCadastrado);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
