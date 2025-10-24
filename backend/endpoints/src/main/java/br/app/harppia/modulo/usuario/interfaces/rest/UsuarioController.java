package br.app.harppia.modulo.usuario.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.usuario.application.usecases.CadastrarUsuarioUseCase;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastradoDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastroDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UsuarioController {

	private final CadastrarUsuarioUseCase userCadService;

	public UsuarioController(CadastrarUsuarioUseCase userCadService) {
		this.userCadService = userCadService;
	}

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UsuarioCadastradoDTO> cadastrarUsuario(
			@RequestPart(value = "user_data") @Valid UsuarioCadastroDTO usrCadDTO,
			@RequestPart(value = "profile_photo", required = false) MultipartFile file) {

		UsuarioCadastradoDTO userCadastrado = userCadService.execute(usrCadDTO, file);

		if(userCadastrado != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(userCadastrado);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}