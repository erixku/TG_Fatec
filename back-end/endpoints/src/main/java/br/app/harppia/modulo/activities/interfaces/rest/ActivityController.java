package br.app.harppia.modulo.activities.interfaces.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.activities.application.usecase.BuscarPublicacoesUseCase;
import br.app.harppia.modulo.activities.application.usecase.CriarAtividadeUseCase;
import br.app.harppia.modulo.activities.domain.request.BuscarPublicacoesRequest;
import br.app.harppia.modulo.activities.domain.request.CriarAtividadeRequest;
import br.app.harppia.modulo.activities.domain.response.BuscarPublicacoesResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/activity")
@RequiredArgsConstructor
public class ActivityController {

	private final CriarAtividadeUseCase criAtvUC;
	private final BuscarPublicacoesUseCase busPubUC;

	@PostMapping("/create")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO') "
			+ "&& @harppiaSecurityService.hasChurchRole(#criAtvReq.idIgreja, 'MEMBRO') "
			+ "&& @harppiaSecurityService.hasMinistryRole(#criAtvReq.idMinisterio, 'MINISTRO')")
	public ResponseEntity<String> criar(@RequestBody CriarAtividadeRequest criAtvReq) {

		boolean blnResult = criAtvUC.proceder(criAtvReq);

		return ResponseEntity.status((!blnResult) ? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(null);
	}

	@PostMapping("/{church}/list/all")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO') "
			+ "&& @harppiaSecurityService.hasChurchRole(#idIgreja, 'MEMBRO') ")
	public ResponseEntity<BuscarPublicacoesResponse> buscarTodasPorIgreja(
			@PathVariable("church") UUID idIgreja,
			@RequestBody BuscarPublicacoesRequest busPubReqParametros
		) {
		
		BuscarPublicacoesResponse result = busPubUC.listFromChurch(idIgreja, busPubReqParametros);
	
		return ResponseEntity.status((result == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(result);
	}
	
	@PostMapping("/{church}/list/{ministry}/all")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO') "
			+ "&& @harppiaSecurityService.hasChurchRole(#idIgreja, 'MEMBRO') "
			+ "&& @harppiaSecurityService.hasMinistryRole(#idMinisterio, 'LEVITA')")
	public ResponseEntity<BuscarPublicacoesResponse> buscarTodasPorMinisterio(
			@PathVariable("church") UUID idIgreja,
			@PathVariable("ministry") UUID idMinisterio,
			@RequestBody BuscarPublicacoesRequest busPubReqParametros
		) {
		
		BuscarPublicacoesResponse result = busPubUC.listFromMinistry(idIgreja, idMinisterio, busPubReqParametros);
	
		return ResponseEntity.status((result == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(result);
	}
}
