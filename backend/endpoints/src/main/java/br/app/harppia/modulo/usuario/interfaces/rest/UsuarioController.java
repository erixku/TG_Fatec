package br.app.harppia.modulo.usuario.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.usuario.application.usecases.AtualizarUsuarioUseCase;
import br.app.harppia.modulo.usuario.application.usecases.CadastrarUsuarioUseCase;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;
import br.app.harppia.modulo.usuario.application.usecases.DeletarUsuarioUseCase;
import br.app.harppia.modulo.usuario.domain.dto.AtualizarUsuarioDTO;
import br.app.harppia.modulo.usuario.domain.dto.InformacaoPublicaUsuarioDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastradoDTO;
import br.app.harppia.modulo.usuario.domain.dto.register.UsuarioCadastroDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UsuarioController {

	private final CadastrarUsuarioUseCase cadastroService;
	private final ConsultarUsuarioUseCase consultaService;
	private final AtualizarUsuarioUseCase atualizarService;
	private final DeletarUsuarioUseCase deletarService;

	public UsuarioController(CadastrarUsuarioUseCase userCadService, ConsultarUsuarioUseCase consultaService,
			AtualizarUsuarioUseCase atualizarService, DeletarUsuarioUseCase deletarService) {
		this.cadastroService = userCadService;
		this.consultaService = consultaService;
		this.atualizarService = atualizarService;
		this.deletarService = deletarService;
	}

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UsuarioCadastradoDTO> cadastrarUsuario(
			@RequestPart(value = "user_data") @Valid UsuarioCadastroDTO usrCadDTO,
			@RequestPart(value = "profile_photo", required = false) MultipartFile file) {

		UsuarioCadastradoDTO userCadastrado = cadastroService.execute(usrCadDTO, file);

		if (userCadastrado == null)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(userCadastrado);
	}

	@GetMapping(value = "/find/{key}")
	public ResponseEntity<InformacaoPublicaUsuarioDTO> buscarUsuario(String key) {
		if (key.trim().isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		InformacaoPublicaUsuarioDTO response;

		response = consultaService.buscarUsuarioPorCpfOuEmailOuTelefone(key, key, key);

		if (response == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}

	/**
	 * Os dados de um usuário só podem ser atualizados na tela de:
	 * 		- informações pessoais;
	 * 		- redefinição de senha;
	 * 		- toda vez que ele entrar/sair da aplicação.
	 * 
	 * Obs: se ele excluir a própria conta, ele apenas atualiza o campo "is_disabled" (ou equivalente) para 'true'
	 * @param dto
	 * @return
	 */
	@PutMapping(value = "/update/{key}")
	public ResponseEntity<Boolean> atualizarUsuario(AtualizarUsuarioDTO dto) {
		if (dto == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		boolean response;

		response = atualizarService.execute(dto);

		if (!response)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deletarUsuario(String uuid){
		deletarService.getClass();
	}
}