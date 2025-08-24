package br.app.harppia.usuario.autenticacao.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.usuario.autenticacao.dtos.AutenticacaoUsuarioDTO;
import br.app.harppia.usuario.autenticacao.service.AutenticacaoUsuarioService;
import br.app.harppia.usuario.cadastro.dtos.UsuarioCadastroDTO;
import br.app.harppia.usuario.cadastro.service.UsuarioCadastroService;
import jakarta.validation.Valid;


/**
 * Responsável por receber todas as requisições relacionadas a:
 * pedidos de autenticação, seja via email, SMS ou outrem.
 * 
 * Rota controlada: "users/v1/auth/"
 * 
 * @author asher_ren
 * @since 15/08/2025
 */
@RestController
@RequestMapping("users/v1/auth/")
public class AutenticacaoUsuarioController {

	private final AutenticacaoUsuarioService authUserService;
	
	public AutenticacaoUsuarioController(AutenticacaoUsuarioService authUserService) {
		this.authUserService = authUserService;
	}
	
	@PostMapping("autenticar/")
	public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid AutenticacaoUsuarioDTO authUserDTO) {
		authUserService.autenticarUsuario(authUserDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
