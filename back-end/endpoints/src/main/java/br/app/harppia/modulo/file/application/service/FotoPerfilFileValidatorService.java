package br.app.harppia.modulo.file.application.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;

public class FotoPerfilFileValidatorService extends FileValidatorService {

	private static final List<EMimeTypeArquivo> mimeTypesPermitidos = List.of(EMimeTypeArquivo.IMAGE_JPEG,
			EMimeTypeArquivo.IMAGE_PNG, EMimeTypeArquivo.IMAGE_SVG_PLUS_XML);

	private static final List<EExtensaoArquivo> extensoesPermitidas = List.of(EExtensaoArquivo.PNG,
			EExtensaoArquivo.JPG, EExtensaoArquivo.JPEG, EExtensaoArquivo.SVG);

	public FotoPerfilFileValidatorService(BucketRestricoesUploadInfo bucketContraints) {
		super(bucketContraints);
	}

	public boolean isFileValidToProfilePhoto(MultipartFile file) throws IOException {

		if (!mimeTypesPermitidos.contains(EMimeTypeArquivo.fromValue(file.getContentType())))
			return false;

		if (!mimeTypesPermitidos.contains(EMimeTypeArquivo.fromValue(FileValidatorService.detectRealMimeType(file))))
			return false;

		if (!extensoesPermitidas.contains(EExtensaoArquivo.fromValue(pegarExtensao(file.getOriginalFilename()))))
			return false;

		return true;
	}

}
