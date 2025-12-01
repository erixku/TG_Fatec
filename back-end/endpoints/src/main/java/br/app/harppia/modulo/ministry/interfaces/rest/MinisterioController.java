package br.app.harppia.modulo.ministry.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.ministry.application.usecase.AtualizarMinisterioUseCase;
import br.app.harppia.modulo.ministry.application.usecase.ConsultarMinisterioUseCase;
import br.app.harppia.modulo.ministry.application.usecase.CriarMinisterioUseCase;
import br.app.harppia.modulo.ministry.application.usecase.DeletarMinisterioUseCase;
import br.app.harppia.modulo.ministry.domain.request.CriarMinisterioRequest;
import br.app.harppia.modulo.ministry.domain.response.CriarMinisterioResponse;

@RestController
@RequestMapping("/v1/ministry")
public class MinisterioController {

	private final CriarMinisterioUseCase criMinUC;
	private final ConsultarMinisterioUseCase conMinUC;
	private final AtualizarMinisterioUseCase atuMinUC;
	private final DeletarMinisterioUseCase delMinUC;
	
	public MinisterioController(CriarMinisterioUseCase criMinUC, ConsultarMinisterioUseCase conMinUC,
			AtualizarMinisterioUseCase atuMinUC, DeletarMinisterioUseCase delMinUC) {
		this.criMinUC = criMinUC;
		this.conMinUC = conMinUC;
		this.atuMinUC = atuMinUC;
		this.delMinUC = delMinUC;
	}

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CriarMinisterioResponse> criar(
			@RequestPart("ministry_data") CriarMinisterioRequest criMinReq,
			@RequestPart("ministry_photo") MultipartFile mtpFile
		) {
		
		CriarMinisterioResponse criMinResCriado = criMinUC.proceder(criMinReq, mtpFile);
		
		if(criMinResCriado == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(criMinResCriado);
			
		return ResponseEntity.status(HttpStatus.OK).body(criMinResCriado);
	}
	
}
