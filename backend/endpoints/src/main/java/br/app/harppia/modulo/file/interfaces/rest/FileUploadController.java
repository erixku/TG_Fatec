package br.app.harppia.modulo.file.interfaces.rest;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.application.usecases.SalvarArquivoUseCase;
import br.app.harppia.modulo.file.domain.entities.Arquivo;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoPersistidoResponse;

@RestController
@RequestMapping("/v1/files")
public class FileUploadController {

	private final SalvarArquivoUseCase salvarArquivoUseCase;

	public FileUploadController(SalvarArquivoUseCase salvarArquivoUseCase) {
		this.salvarArquivoUseCase = salvarArquivoUseCase;
	}

	@PostMapping("/upload")
	public ResponseEntity<ArquivoPersistidoResponse> uploadFileToS3(@RequestParam("file") MultipartFile file,
			String bucketToSave) {
		try {
			Arquivo arquivoSalvo = salvarArquivoUseCase.salvar(file, bucketToSave);

			ArquivoPersistidoResponse arqResponse = new ArquivoPersistidoResponse(arquivoSalvo.getLinkPublico());

			return ResponseEntity.ok(arqResponse);
		} catch (IOException e) {
			return ResponseEntity.status(500).body(null);
		}
	}
}