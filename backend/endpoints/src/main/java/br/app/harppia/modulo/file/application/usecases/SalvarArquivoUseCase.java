package br.app.harppia.modulo.file.application.usecases;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.application.service.FileStreamService;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoPersistidoResponse;
import br.app.harppia.modulo.file.infrastructure.repository.ArquivoRepository;
import br.app.harppia.modulo.file.infrastructure.repository.entities.ArquivoEntity;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;

@Service
public class SalvarArquivoUseCase {

	private final ArquivoRepository arquivoRepo;
	private final FileStreamService uploadService;

	public SalvarArquivoUseCase(ArquivoRepository arquivoRepo, FileStreamService uploadService) {
		this.arquivoRepo = arquivoRepo;
		this.uploadService = uploadService;
	}

	public ArquivoPersistidoResponse salvar(MultipartFile file, String bucket) throws IOException {
		// Salva o arquivo na nuvem
		uploadService.uploadFile(file, bucket);

		ArquivoEntity arquivo = new ArquivoEntity(null, null, null, null, null, false, file.getName(),
				EMimeTypeArquivo.valueOf(file.getContentType()), EExtensaoArquivo.valueOf(file.getContentType()),
				file.getSize(), null);

		// Salva o arquivo no banco
		ArquivoEntity arquivoSalvo = arquivoRepo.save(arquivo);
		
		return new ArquivoPersistidoResponse(arquivoSalvo.getUuid(), arquivoSalvo.getNome());
	}
}
