package br.app.harppia.controllers.auth.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.model.auth.dto.UsuarioCadastroDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user/v1/")
public class UsuarioController {

	private final UsuarioService userService;
	
	public UsuarioController(UsuarioService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("cadastro/")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO usrCadDTO) {
		userService.cadastrarUsuario(usrCadDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
