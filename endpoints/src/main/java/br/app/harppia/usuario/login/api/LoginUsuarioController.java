package br.app.harppia.usuario.login.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.usuario.login.dtos.LoginUsuarioDTO;
import br.app.harppia.usuario.login.service.LoginUsuarioService;
import jakarta.validation.Valid;

/**
 * Responsável por receber todas as requisições relacionadas ao LOGIN de 
 * usuários no sistema.
 * 
 * Rota controlada: "users/"
 * 
 * @author asher_ren
 * @since 15/08/2025
 */
		
@RestController
@RequestMapping("users/v1/")
public class LoginUsuarioController {

	private final LoginUsuarioService loginUsuarioService;
	
	public LoginUsuarioController(LoginUsuarioService loginUsuarioService) {
		this.loginUsuarioService = loginUsuarioService;
	}
	
	/**
	 * Esse método procura pelo registro do usuário que tentou logar. 
	 * Ele verifica se a credencial inserida existe e depois se a senha
	 * está de acordo com a persistida no banco.
	 * 
	 * Os dados são recuperados em formato JSON pelo cliente e convertidos 
	 * numa classe java (LoginUsuarioDTO).
	 */
	@PostMapping("signin")
	public ResponseEntity<?> verificarLoginUsuario(@RequestBody @Valid LoginUsuarioDTO loginUsuarioCadastro) {
		loginUsuarioService.verificarLoginUsuario(loginUsuarioCadastro);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
