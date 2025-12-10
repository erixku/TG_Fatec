package br.app.harppia.modulo.music.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.app.harppia.modulo.music.application.usecase.BuscarMusicaUseCase;
import br.app.harppia.modulo.music.application.usecase.CadastrarMusicaUseCase;
import br.app.harppia.modulo.music.domain.request.BuscarMusicaRequest;
import br.app.harppia.modulo.music.domain.request.CadastroMusicaRequest;
import br.app.harppia.modulo.music.domain.response.BuscarMusicaResponse;
import br.app.harppia.modulo.music.domain.response.CadastroMusicaResponse;

@RestController
@RequestMapping("/v1/song")
public class MusicController {

	private final CadastrarMusicaUseCase cadMusUC;
	private final BuscarMusicaUseCase busMusUC;

	public MusicController(CadastrarMusicaUseCase cadMusUC, BuscarMusicaUseCase busMusUC) {
		this.cadMusUC = cadMusUC;
		this.busMusUC = busMusUC;
	}

	@PostMapping("/create")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO')")
	public ResponseEntity<CadastroMusicaResponse> salvar(@RequestBody CadastroMusicaRequest requestDto) {
		CadastroMusicaResponse cadMusRes = cadMusUC.salvar(requestDto);

		return cadMusRes != null
				? ResponseEntity.status(HttpStatus.OK).body(cadMusRes)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/find")
	@PreAuthorize("@harppiaSecurityService.hasSystemRole('USUARIO')")
	public ResponseEntity<BuscarMusicaResponse> buscar(@RequestParam("nome") String nome){
		
		BuscarMusicaResponse busMusRes = busMusUC.buscar(new BuscarMusicaRequest(nome));
		
		return busMusRes != null
				? ResponseEntity.status(HttpStatus.OK).body(busMusRes)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
}
