package br.app.harppia.modulo.usuario.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users/perfil")
public class PerfilUsuarioController {

	@GetMapping("/{id}")
	public UsuarioPerfilDTO buscarPerfilUsuario() {
		return null;
	}
	
	@PutMapping
}
