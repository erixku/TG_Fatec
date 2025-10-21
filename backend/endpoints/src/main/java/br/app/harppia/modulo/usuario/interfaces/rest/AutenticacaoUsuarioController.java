package br.app.harppia.modulo.usuario.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.auth.domain.AutenticacaoUsuarioDTO;
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
@RequestMapping("/v1/users")
public class AutenticacaoUsuarioController {
	
	@Autowired
	private AuthenticationManager authManager;

	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid AutenticacaoUsuarioDTO authUserDTO) {
		
		var userToken = new UsernamePasswordAuthenticationToken(authUserDTO.getUsername(), authUserDTO.getPassword());
		var auth = this.authManager.authenticate(userToken);
		
		return ResponseEntity.ok().build();
	}
}
