package br.app.harppia.modulo.testes;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.auth.domain.valueobject.InformacoesAutenticacaoUsuarioRVO;
import br.app.harppia.modulo.usuario.infrasctructure.adapter.ConsultarUsuarioFromAuthAdapter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestesController {

	private final ConsultarUsuarioFromAuthAdapter consultaTeste;
	
	@GetMapping("/consulta-roles")
	public ResponseEntity<InformacoesAutenticacaoUsuarioRVO> testar(@RequestParam("id") UUID idUsuario) {
		
		InformacoesAutenticacaoUsuarioRVO result = consultaTeste.porId(idUsuario);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
	}
	
	@GetMapping("/test-church-role-proprietario")
	@PreAuthorize("@testSecurityService.hasChurchRole(#idIgreja, 'adm_proprietario')")
	public ResponseEntity<String> testarChurchProprietario(UUID idIgreja) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deu bom, malandro 2");
	}

	@GetMapping("/test-ministry-role-ministro")
	@PreAuthorize("@testSecurityService.hasMinistryRole(#idMinisterio, #role)")
	public ResponseEntity<String> testarMinisterioMinistro(UUID idMinisterio, String role) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deu bom, malandro 3");
	}
}
