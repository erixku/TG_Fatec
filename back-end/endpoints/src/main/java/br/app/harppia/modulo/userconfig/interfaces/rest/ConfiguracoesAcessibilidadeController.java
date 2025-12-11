package br.app.harppia.modulo.userconfig.interfaces.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.userconfig.application.usecases.AtualizarConfiguracoesAcessibilidadeUseCase;
import br.app.harppia.modulo.userconfig.application.usecases.SalvarConfiguracoesAcessibilidadeUseCase;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeAuditivaRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeIntelectualRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeVisualRequest;

@RestController
@RequestMapping("/v1/config")
public class ConfiguracoesAcessibilidadeController {

	private final SalvarConfiguracoesAcessibilidadeUseCase scaus;
	private final AtualizarConfiguracoesAcessibilidadeUseCase acaus;
	
	public ConfiguracoesAcessibilidadeController(SalvarConfiguracoesAcessibilidadeUseCase scaus,
			AtualizarConfiguracoesAcessibilidadeUseCase acaus) {
		this.scaus = scaus;
		this.acaus = acaus;
	}

	@PostMapping("/save")
	@PreAuthorize("hasRole('ANONIMO')")
	public ResponseEntity<String> salvarTodas(@RequestParam("idDonoConfig") String idDonoCfg){
		
		UUID idDonoCfgStr = UUID.fromString(idDonoCfg);
		
		scaus.todas(
				new SalvarAcessibilidadeAuditivaRequest(idDonoCfgStr),
				new SalvarAcessibilidadeVisualRequest(idDonoCfgStr),
				new SalvarAcessibilidadeIntelectualRequest(idDonoCfgStr)
			);
		
		return ResponseEntity.ok(idDonoCfg);
	}
}
