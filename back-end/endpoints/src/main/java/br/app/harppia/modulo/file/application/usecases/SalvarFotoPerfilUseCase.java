package br.app.harppia.modulo.file.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.file.application.service.FileStreamService;
import br.app.harppia.modulo.file.application.service.FileValidatorService;
import br.app.harppia.modulo.file.application.service.FotoPerfilFileValidatorService;
import br.app.harppia.modulo.file.domain.entities.Arquivo;
import br.app.harppia.modulo.file.domain.response.ArquivoPersistidoResponse;
import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfoCVO;
import br.app.harppia.modulo.file.infrastructure.repository.ArquivoRepository;
import br.app.harppia.modulo.file.infrastructure.repository.entities.ArquivoEntity;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

@Service
public class SalvarFotoPerfilUseCase {

	private final ArquivoRepository arquivoRepo;
	private final BuscarBucketUseCase buscarBucket;
	private final FileStreamService uploadService;
	private FileValidatorService fileValidator;
	private FotoPerfilFileValidatorService fotoPerfilValidator;

	public SalvarFotoPerfilUseCase(ArquivoRepository arquivoRepo, BuscarBucketUseCase buscarBucket,
			FileStreamService uploadService) {
		this.arquivoRepo = arquivoRepo;
		this.buscarBucket = buscarBucket;
		this.uploadService = uploadService;
	}

	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	@Transactional
	public ArquivoPersistidoResponse salvar(MultipartFile file, String bucket, UUID criador)  {

		if(bucket.contains("/"))
			throw new GestaoArquivoException("O nome da pasta não deve conter '/'");
		
		BucketRestricoesUploadInfoCVO bucketContraints = buscarBucket.findByNome(ENomeBucket.fromNome(bucket));
	
		fileValidator = new FileValidatorService(bucketContraints);
		if(!fileValidator.arquivoEstaValido(file))
			throw new GestaoArquivoException("Arquivo inválido ou corrompido!");
		
		fotoPerfilValidator = new FotoPerfilFileValidatorService(bucketContraints);
		if(!fotoPerfilValidator.isFileValidToProfilePhoto(file))
			throw new GestaoArquivoException("Tipo de arquivo não permitido!");
		
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
				(int) file.getSize(), 
				bucketContraints.getId()
			);

		ArquivoEntity arquivoSalvo = arquivoRepo.save(arquivoParaSalvar);

		return new ArquivoPersistidoResponse(arquivoSalvo.getId(), arquivoSalvo.getNome());
	}
}
