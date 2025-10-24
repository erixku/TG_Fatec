package br.app.harppia.modulo.file.interfaces.rest;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.application.usecases.SalvarArquivoUseCase;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoPersistidoResponse;

@RestController
@RequestMapping("/v1/files")
public class FileUploadController {

	private final SalvarArquivoUseCase salvarArquivoUseCase;

	public FileUploadController(SalvarArquivoUseCase salvarArquivoUseCase) {
		this.salvarArquivoUseCase = salvarArquivoUseCase;
	}

	@PostMapping("/upload")
	public ResponseEntity<ArquivoPersistidoResponse> uploadFile(@RequestParam("file") MultipartFile file,
			String bucketToSave) {
		try {
			ArquivoPersistidoResponse arquivoSalvo = salvarArquivoUseCase.salvar(file, bucketToSave);
			
			return ResponseEntity.ok(arquivoSalvo);
		} catch (IOException e) {
			return ResponseEntity.status(500).body(null);
		}
	}
}