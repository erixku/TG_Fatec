package br.app.harppia.modulo.file.application.usecases;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.ArquivoInvalidoException;
import br.app.harppia.defaults.custom.exceptions.RegistrarArquivoException;
import br.app.harppia.modulo.file.application.service.FileStreamService;
import br.app.harppia.modulo.file.application.service.FileValidatorService;
import br.app.harppia.modulo.file.domain.entities.Arquivo;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoPersistidoResponse;
import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfo;
import br.app.harppia.modulo.file.infrastructure.repository.ArquivoRepository;
import br.app.harppia.modulo.file.infrastructure.repository.entities.ArquivoEntity;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

@Service
public class SalvarArquivoUseCase {

	private final ArquivoRepository arquivoRepo;
	private final BuscarBucketUseCase buscarBucket;
	private final FileStreamService uploadService;
	private FileValidatorService fileValidator;

	public SalvarArquivoUseCase(ArquivoRepository arquivoRepo, BuscarBucketUseCase buscarBucket,
			FileStreamService uploadService) {
		this.arquivoRepo = arquivoRepo;
		this.buscarBucket = buscarBucket;
		this.uploadService = uploadService;
	}

	public ArquivoPersistidoResponse salvar(MultipartFile file, String bucket, UUID criador) throws IOException, RegistrarArquivoException, ArquivoInvalidoException {

		if(bucket.contains("/"))
			throw new RegistrarArquivoException("O nome da pasta não deve conter '/'");
		
		BucketRestricoesUploadInfo bucketContraints = buscarBucket.findByNome(ENomeBucket.fromNome(bucket));
	
		fileValidator = new FileValidatorService(bucketContraints);
		
		if(!fileValidator.arquivoEstaValido(file))
			throw new RegistrarArquivoException("Arquivo inválido ou corrompido!");
		
		// o arquivo é renomeado aqui
		Arquivo arquivoSalvoNuvem = uploadService.uploadFile(file, bucket, criador);
		
		String extensao = fileValidator.pegarExtensao(file.getOriginalFilename());
		
		ArquivoEntity arquivoParaSalvar;
		arquivoParaSalvar = new ArquivoEntity(
				null, 
				null, 
				null, 
				criador, 
				null, 
				false, 
				arquivoSalvoNuvem.getNomeArquivo(),
				arquivoSalvoNuvem.getLinkPublico(),
				EMimeTypeArquivo.fromValue(file.getContentType()), 
				EExtensaoArquivo.fromValue(extensao),
				file.getSize(), 
				bucketContraints.id()
			);

		ArquivoEntity arquivoSalvo = arquivoRepo.save(arquivoParaSalvar);

		return new ArquivoPersistidoResponse(arquivoSalvo.getId(), arquivoSalvo.getNome());
	}
}
