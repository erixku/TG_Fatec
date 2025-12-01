package br.app.harppia.modulo.file.interfaces.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.application.usecases.SalvarFotoPerfilUseCase;
import br.app.harppia.modulo.file.domain.response.ArquivoPersistidoResponse;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

@RestController
@RequestMapping("/v1/files")
public class FileUploadController {

	private final SalvarFotoPerfilUseCase slvFotoPrfUC;

	public FileUploadController(SalvarFotoPerfilUseCase slvFotoPrfUsrUC) {
		this.slvFotoPrfUC = slvFotoPrfUsrUC;
	}

	@PostMapping(value = "/upload/user_profile_photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ArquivoPersistidoResponse> uploadFotoPerfilUsuario(
				@RequestPart("file") MultipartFile file,
				@RequestPart("id_criador") UUID idCriador
			) {
			
		ArquivoPersistidoResponse arqPstRes;
		arqPstRes = slvFotoPrfUC.proceder(file, ENomeBucket.FOTO_PERFIL_USUARIO.getCustomValue(), idCriador);

		return ResponseEntity.status( 
				(arqPstRes != null) 
					? HttpStatus.CREATED
					: HttpStatus.BAD_REQUEST
			).body(arqPstRes);
	}

	@PostMapping(value = "/upload/profile_picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ArquivoPersistidoResponse> uploadFotoPerfilIgreja(
			@RequestPart("file") MultipartFile file,
			@RequestPart("id_criador") UUID idCriador
		) {
		
		ArquivoPersistidoResponse arqPstRes;
		arqPstRes = slvFotoPrfUC.proceder(file, ENomeBucket.FOTO_PERFIL_IGREJA.getCustomValue(), idCriador);
		
		return ResponseEntity.status( 
				(arqPstRes != null) 
				? HttpStatus.CREATED
				: HttpStatus.BAD_REQUEST
				).body(arqPstRes);
	}
}