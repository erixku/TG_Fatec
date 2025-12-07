package br.app.harppia.modulo.ministry.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.ministry.application.usecase.AdicionarMembroMinisterioUseCase;
import br.app.harppia.modulo.ministry.application.usecase.AtualizarMinisterioUseCase;
import br.app.harppia.modulo.ministry.application.usecase.ConsultarMinisterioUseCase;
import br.app.harppia.modulo.ministry.application.usecase.CriarMinisterioUseCase;
import br.app.harppia.modulo.ministry.application.usecase.DeletarMinisterioUseCase;
import br.app.harppia.modulo.ministry.domain.request.AdicionarMembroRequest;
import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioRequest;
import br.app.harppia.modulo.ministry.domain.request.BuscarMinisterioResponse;
import br.app.harppia.modulo.ministry.domain.request.CriarMinisterioRequest;
import br.app.harppia.modulo.ministry.domain.response.AdicionarMembroResponse;
import br.app.harppia.modulo.ministry.domain.response.CriarMinisterioResponse;
import br.app.harppia.modulo.ministry.domain.response.ListarMinisteriosResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/ministry")
@RequiredArgsConstructor
public class MinisterioController {

	private final CriarMinisterioUseCase criMinUC;
	private final ConsultarMinisterioUseCase conMinUC;
	private final AtualizarMinisterioUseCase atuMinUC;
	private final DeletarMinisterioUseCase delMinUC;
	private final AdicionarMembroMinisterioUseCase adcMemMinUC;

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO') "
			+ "&& @harppiaSecurityService.hasChurchRole(#criMinReq.idIgreja, 'ADM_PROPRIETARIO')")
	public ResponseEntity<CriarMinisterioResponse> criar(
			@RequestPart("ministry_data") CriarMinisterioRequest criMinReq,
			@RequestPart("ministry_photo") MultipartFile mtpFile
		) {

		CriarMinisterioResponse criMinResCriado = criMinUC.proceder(criMinReq, mtpFile);

		if (criMinResCriado == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(criMinResCriado);

		return ResponseEntity.status(HttpStatus.OK).body(criMinResCriado);
	}
	
	@GetMapping("/search")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO') "
			+ "&& @harppiaSecurityService.hasChurchRole(#busMinReq.idIgreja, 'levita')")
	public ResponseEntity<BuscarMinisterioResponse> buscar(BuscarMinisterioRequest busMinReq) {	
		
		BuscarMinisterioResponse lstMinRes = conMinUC.porNome(busMinReq);
		
		return ResponseEntity.status((lstMinRes == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(lstMinRes);		
	}
	
	@GetMapping("/search/all")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO') "
			+ "&& @harppiaSecurityService.hasChurchRole(#busMinReq.idIgreja, 'levita')")
	public ResponseEntity<ListarMinisteriosResponse> listarTodos(BuscarMinisterioRequest busMinReq) {
		
		ListarMinisteriosResponse lstMinRes = conMinUC.listarPorNome(busMinReq);
		
		return ResponseEntity.status((lstMinRes == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(lstMinRes);		
	}

	@PostMapping("/add/member")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO') "
			+ "&& @harppiaSecurityService.hasMinistryRole(#adcMbmReq.idMinisterio, 'lider')")
	public ResponseEntity<AdicionarMembroResponse> adicionarMembro(@RequestBody AdicionarMembroRequest adcMbmReq) {

		AdicionarMembroResponse adcMbmRes = adcMemMinUC.adicionarUm(adcMbmReq);

		return ResponseEntity.status((adcMbmRes == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(adcMbmRes);
	}

}
