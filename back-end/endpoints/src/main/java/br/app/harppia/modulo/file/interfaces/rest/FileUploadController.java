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

@RestController
@RequestMapping("/v1/files")
public class FileUploadController {

	private final SalvarFotoPerfilUseCase salvarFotoPerfilUseCase;

	public FileUploadController(SalvarFotoPerfilUseCase salvarFotoPerfilUseCase) {
		this.salvarFotoPerfilUseCase = salvarFotoPerfilUseCase;
	}

	@PostMapping(value = "/upload/profile_picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ArquivoPersistidoResponse> uploadProfilePictureFile(
				@RequestPart("file") MultipartFile file,
				@RequestPart("bucket") String bucketToSave,
				@RequestPart("id-criador") UUID idCriador) {
			
		ArquivoPersistidoResponse arquivoSalvo = null;
		
		arquivoSalvo = salvarFotoPerfilUseCase.salvar(file, bucketToSave, idCriador);

		return ResponseEntity.status( 
				(arquivoSalvo != null) 
					? HttpStatus.CREATED
					: HttpStatus.INTERNAL_SERVER_ERROR
			).body(arquivoSalvo);
	}
}