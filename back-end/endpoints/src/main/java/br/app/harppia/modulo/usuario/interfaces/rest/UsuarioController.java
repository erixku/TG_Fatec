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
	private final DeletarUsuarioUseCase   delUsrUC;

	public UsuarioController(CadastrarUsuarioUseCase cadUsrUC, ConsultarUsuarioUseCase conUsrUC,
			AtualizarUsuarioUseCase atuUsrUC, DeletarUsuarioUseCase delUsrUC) {
		this.cadUsrUC = cadUsrUC;
		this.conUsrUC = conUsrUC;
		this.atuUsrUC = atuUsrUC;
		this.delUsrUC = delUsrUC;
	}

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UsuarioCadastradoDTO> cadastrar(
			@RequestPart(value = "user_data") @Valid UsuarioCadastroDTO usrCadDTO,
			@RequestPart(value = "profile_photo", required = false) MultipartFile file) {

		UsuarioCadastradoDTO usrCadDto = cadUsrUC.execute(usrCadDTO, file);

		return ResponseEntity.status((usrCadDto != null) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(usrCadDto);
	}

	@GetMapping(value = "/find")
	@PreAuthorize("@churchSecurity.check(authentication, #churchId, 'LEVITA')")
	public ResponseEntity<InformacaoPublicaUsuarioDTO> buscar(@RequestParam("key") String key) {

		InformacaoPublicaUsuarioDTO response = conUsrUC.porCpfOuEmailOuTelefone(key);

		return ResponseEntity.status((response != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);

	}

	/**
	 * Os dados de um usuário só podem ser atualizados na tela de: - informações
	 * pessoais; - redefinição de senha; - toda vez que ele entrar/sair da
	 * aplicação.
	 * 
	 * Obs: se ele excluir a própria conta, ele apenas atualiza o campo
	 * "is_disabled" (ou equivalente) para 'true'
	 * 
	 * @param dto
	 * @return
	 */
	@PutMapping(value = "/update/{key}")
	@PreAuthorize("@churchSecurity.check(authentication, #churchId, 'LEVITA')")
	public ResponseEntity<Boolean> atualizarUsuario(AtualizarUsuarioDTO dto) {

		boolean response = atuUsrUC.execute(dto);

		return ResponseEntity.status((response) ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("@churchSecurity.check(authentication, #churchId, 'LEVITA')")
	public void deletarUsuario(String uuid) {
		delUsrUC.getClass();
	}
}