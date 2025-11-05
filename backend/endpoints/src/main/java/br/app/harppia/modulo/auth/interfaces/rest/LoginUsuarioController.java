package br.app.harppia.modulo.auth.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.auth.application.services.AutenticarUsuarioService;
import br.app.harppia.modulo.auth.application.usecases.LogarUsuarioUseCase;
import br.app.harppia.modulo.auth.domain.auth.request.RefreshTokenRequest;
import br.app.harppia.modulo.auth.domain.auth.response.RefreshTokenResponse;
import br.app.harppia.modulo.auth.domain.login.request.LoginUsuarioRequest;
import br.app.harppia.modulo.auth.domain.login.response.LoginUsuarioResponse;

/**
 * Responsável por receber todas as requisições relacionadas ao LOGIN de
 * usuários no sistema.
 * 
 * Rota controlada: "/users"
 * 
 * @author asher_ren
 * @since 15/08/2025
 */

@RestController
@RequestMapping("/v1/users")
public class LoginUsuarioController {

	private final LogarUsuarioUseCase loginService;
	private final AutenticarUsuarioService authService;

	public LoginUsuarioController(LogarUsuarioUseCase loginService, AutenticarUsuarioService authService) {
		this.loginService = loginService;
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginUsuarioResponse> login(@RequestBody LoginUsuarioRequest loginDto) {
		LoginUsuarioResponse response = loginService.execute(loginDto);

		return (response == null) 
				? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
				: ResponseEntity.status(HttpStatus.FOUND).body(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<RefreshTokenResponse> atualizar(@RequestBody RefreshTokenRequest request){
		
		RefreshTokenResponse response = authService.atualizarToken(request);
		
		return (response == null) 
				? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
				: ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
