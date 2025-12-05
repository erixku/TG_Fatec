package br.app.harppia.modulo.church.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.church.application.usecase.BuscarIgrejaUseCase;
import br.app.harppia.modulo.church.application.usecase.CriarIgrejaUseCase;
import br.app.harppia.modulo.church.domain.request.BuscarIgrejaRequest;
import br.app.harppia.modulo.church.domain.request.CadastroIgrejaRequest;
import br.app.harppia.modulo.church.domain.response.BuscarListaIgrejasResponse;
import br.app.harppia.modulo.church.domain.response.CadastroIgrejaResponse;

@RestController
@RequestMapping("/v1/church")
public class IgrejaController {
	
	private final CriarIgrejaUseCase criIgrUS;
	private final BuscarIgrejaUseCase bscIgrUS;

	public IgrejaController(CriarIgrejaUseCase criIgrUS, BuscarIgrejaUseCase bscIgrUS) {
		this.criIgrUS = criIgrUS;
		this.bscIgrUS = bscIgrUS;
	}

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('LEVITA')")
	public ResponseEntity<CadastroIgrejaResponse> cadastrarIgreja(
			@RequestPart(value = "church_data") CadastroIgrejaRequest cadIgrReq,
			@RequestPart(value = "church_photo") MultipartFile mtpFileFoto
		){
		
		CadastroIgrejaResponse responseDto = criIgrUS.proceder(cadIgrReq, mtpFileFoto);
		
		return cadIgrReq != null
				? ResponseEntity.status(HttpStatus.CREATED).body(responseDto)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('LEVITA')")
	public ResponseEntity<BuscarListaIgrejasResponse> buscarIgrejas(BuscarIgrejaRequest requestDto){
		BuscarListaIgrejasResponse responseDto = bscIgrUS.listaContendoNome(requestDto);
		
		return requestDto != null
				? ResponseEntity.status(HttpStatus.OK).body(responseDto)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
	}
}
