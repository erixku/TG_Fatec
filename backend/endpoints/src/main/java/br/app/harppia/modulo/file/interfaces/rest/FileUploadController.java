package br.app.harppia.modulo.file.interfaces.rest;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.ArquivoInvalidoException;
import br.app.harppia.defaults.custom.exceptions.RegistrarArquivoException;
import br.app.harppia.modulo.file.application.usecases.SalvarArquivoUseCase;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoPersistidoResponse;

@RestController
@RequestMapping("/v1/files")
public class FileUploadController {

	private final SalvarArquivoUseCase salvarArquivoUseCase;

	public FileUploadController(SalvarArquivoUseCase salvarArquivoUseCase) {
		this.salvarArquivoUseCase = salvarArquivoUseCase;
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ArquivoPersistidoResponse> uploadFile(
				@RequestPart("file") MultipartFile file,
				@RequestPart("bucket") String bucketToSave,
				@RequestPart("id-criador") UUID idCriador) {
			
		ArquivoPersistidoResponse arquivoSalvo = null;
		try {
			arquivoSalvo = salvarArquivoUseCase.salvar(file, bucketToSave, idCriador);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RegistrarArquivoException | ArquivoInvalidoException e) {
			e.printStackTrace();			
		}
		
		return ResponseEntity.status( 
				(arquivoSalvo != null) 
					? HttpStatus.CREATED
					: HttpStatus.INTERNAL_SERVER_ERROR
			).body(arquivoSalvo);
	}
}