package br.app.harppia.modulo.usuario.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	private final CadastrarUsuarioUseCase cadUsrUC;
	private final ConsultarUsuarioUseCase conUsrUC;
	private final AtualizarUsuarioUseCase atuUsrUC;
	private final DeletarUsuarioUseCase delUsrUC;

	public UsuarioController(CadastrarUsuarioUseCase cadUsrUC, ConsultarUsuarioUseCase conUsrUC,
			AtualizarUsuarioUseCase atuUsrUC, DeletarUsuarioUseCase delUsrUC) {
		this.cadUsrUC = cadUsrUC;
		this.conUsrUC = conUsrUC;
		this.atuUsrUC = atuUsrUC;
		this.delUsrUC = delUsrUC;
	}

	//Registro
	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UsuarioCadastradoDTO> cadastrar(
			@RequestPart(value = "user_data") @Valid UsuarioCadastroDTO usrCadDTO,
			@RequestPart(value = "profile_photo", required = false) MultipartFile file) {
		
		UsuarioCadastradoDTO userCadastrado = cadUsrUC.execute(usrCadDTO, file);

		if (userCadastrado == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(userCadastrado);
	}

	//Consulta
	@PreAuthorize("hasAnyRole('LEVITA', 'MINISTRO', 'ADMINISTRADOR')")
	@GetMapping(value = "/find")
	public ResponseEntity<InformacaoPublicaUsuarioDTO> buscar(@RequestParam("key") String key) {

		InformacaoPublicaUsuarioDTO response = null;
		response = conUsrUC.porCpfOuEmailOuTelefone(key);

		if (response == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
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

		boolean response = atuUsrUC.execute(dto);

		if (!response)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deletarUsuario(String uuid){
		delUsrUC.getClass();
	}
}