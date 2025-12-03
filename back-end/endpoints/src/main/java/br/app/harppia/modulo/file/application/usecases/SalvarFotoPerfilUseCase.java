package br.app.harppia.modulo.file.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.file.application.service.BuscarBucketService;
import br.app.harppia.modulo.file.application.service.FileStreamService;
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

	private final ArquivoRepository arqRep;
	private final BuscarBucketService busBucSvc;
	private final FileStreamService fileStmSvc;
	private FotoPerfilFileValidatorService fotoPrfVldSvc;

	public SalvarFotoPerfilUseCase(ArquivoRepository arqRep, BuscarBucketService busBucSvc,
			FileStreamService fileStmSvc) {
		this.arqRep = arqRep;
		this.busBucSvc = busBucSvc;
		this.fileStmSvc = fileStmSvc;
	}

	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	@Transactional
	public ArquivoPersistidoResponse proceder(MultipartFile file, String bucket, UUID criador) {

		if (bucket.contains("/"))
			throw new GestaoArquivoException("O nome da pasta não deve conter '/'");

		BucketRestricoesUploadInfoCVO bucketContraints = busBucSvc.findByNome(ENomeBucket.fromNome(bucket));

		fotoPrfVldSvc = new FotoPerfilFileValidatorService(bucketContraints);
		if (!fotoPrfVldSvc.arquivoEstaValido(file))
			throw new GestaoArquivoException("Arquivo inválido ou corrompido!");

		if (!fotoPrfVldSvc.isFileValidToProfilePhoto(file))
			throw new GestaoArquivoException("Tipo de arquivo não permitido!");

		// o arquivo é renomeado aqui
		Arquivo arqSalvoNuvem = fileStmSvc.uploadFile(file, bucket, criador);

		String extensao = fotoPrfVldSvc.pegarExtensao(file.getOriginalFilename());

		ArquivoEntity arquivoParaSalvar;
		arquivoParaSalvar = new ArquivoEntity(null, null, null, criador, null, false,
				arqSalvoNuvem.getNomeArquivo(), arqSalvoNuvem.getLinkPublico(),
				EMimeTypeArquivo.fromValue(file.getContentType()), EExtensaoArquivo.fromValue(extensao),
				(int) file.getSize(), bucketContraints.getId());

		ArquivoEntity arquivoSalvo = arqRep.save(arquivoParaSalvar);

		return new ArquivoPersistidoResponse(arquivoSalvo.getId(), arquivoSalvo.getNome());
	}
}
