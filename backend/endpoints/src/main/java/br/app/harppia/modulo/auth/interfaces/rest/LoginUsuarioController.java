package br.app.harppia.modulo.auth.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.auth.application.services.JwtService;
import br.app.harppia.modulo.auth.application.usecases.LogarUsuarioUseCase;
import br.app.harppia.modulo.auth.domain.request.LoginUsuarioDTO;
import br.app.harppia.modulo.auth.domain.response.AuthenticationResponse;
import jakarta.validation.Valid;

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

	private LogarUsuarioUseCase loginUsuarioService;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;

	public LoginUsuarioController(LogarUsuarioUseCase loginUsuarioService, AuthenticationManager authenticationManager,
			JwtService jwtService) {
		this.loginUsuarioService = loginUsuarioService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	/**
	 * Esse método procura pelo registro do usuário que tentou logar. Ele verifica
	 * se a credencial inserida existe e depois se a senha está de acordo com a
	 * persistida no banco.
	 * 
	 * Os dados são recuperados em formato JSON pelo cliente e convertidos numa
	 * classe java (LoginUsuarioDTO).
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> verificarLoginUsuario(@RequestBody @Valid LoginUsuarioDTO loginUsuarioCadastro) {
		boolean result = loginUsuarioService.verificarLoginUsuario(loginUsuarioCadastro);

		return result ? ResponseEntity.status(HttpStatus.CREATED).build()
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// Controller
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginUsuarioDTO record) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(record.email(), record.senha()));
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String token = jwtService.generateToken(user);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

}
