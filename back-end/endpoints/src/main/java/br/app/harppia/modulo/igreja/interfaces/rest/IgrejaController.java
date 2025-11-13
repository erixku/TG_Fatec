package br.app.harppia.modulo.igreja.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.igreja.application.usecase.BuscarIgrejaUseCase;
import br.app.harppia.modulo.igreja.application.usecase.CriarIgrejaUseCase;
import br.app.harppia.modulo.igreja.domain.request.BuscarIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.request.CadastroIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.response.BuscarIgrejaResponse;
import br.app.harppia.modulo.igreja.domain.response.CadastroIgrejaResponse;

@RestController
@RequestMapping("/v1/church")
public class IgrejaController {
	
	private final CriarIgrejaUseCase cius;
	private final BuscarIgrejaUseCase bius;

	public IgrejaController(CriarIgrejaUseCase cius, BuscarIgrejaUseCase bius) {
		this.cius = cius;
		this.bius = bius;
	}

	@PostMapping("/create")
	public ResponseEntity<CadastroIgrejaResponse> cadastrarIgreja(@RequestBody CadastroIgrejaRequest cadIgrReq){
		
		CadastroIgrejaResponse responseDto = cius.cadastrar(cadIgrReq);
		
		return cadIgrReq != null
				? ResponseEntity.status(HttpStatus.CREATED).body(responseDto)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/search")
	public ResponseEntity<BuscarIgrejaResponse> buscarIgreja(BuscarIgrejaRequest requestDto){
		BuscarIgrejaResponse responseDto = bius.buscar(requestDto);
		
		return requestDto != null
				? ResponseEntity.status(HttpStatus.CREATED).body(responseDto)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
	}
}
