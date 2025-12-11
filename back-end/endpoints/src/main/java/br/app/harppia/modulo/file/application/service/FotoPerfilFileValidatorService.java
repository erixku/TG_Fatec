package br.app.harppia.modulo.file.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfoCVO;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;

public class FotoPerfilFileValidatorService extends FileValidatorService {

    private static final List<EMimeTypeArquivo> mimeTypesPermitidos = List.of(
        EMimeTypeArquivo.IMAGE_JPEG,
        EMimeTypeArquivo.IMAGE_PNG,
        EMimeTypeArquivo.IMAGE_SVG_PLUS_XML
    );

    private static final List<EExtensaoArquivo> extensoesPermitidas = List.of(
        EExtensaoArquivo.PNG,
        EExtensaoArquivo.JPG,
        EExtensaoArquivo.JPEG,
        EExtensaoArquivo.SVG
    );
    
    public FotoPerfilFileValidatorService(BucketRestricoesUploadInfoCVO bucketContraints) {
        super(bucketContraints);
    }

    public boolean isFileValidToProfilePhoto(MultipartFile file) {
    	Boolean isValidFile = super.arquivoEstaValido(file);
        EMimeTypeArquivo mimeHeader = EMimeTypeArquivo.fromValue(file.getContentType());
        EMimeTypeArquivo mimeReal = EMimeTypeArquivo.fromValue(FileValidatorService.detectRealMimeType(file));
        EExtensaoArquivo extensao = EExtensaoArquivo.fromValue(pegarExtensao(file.getOriginalFilename()));

        return isValidFile
        	&& mimeTypesPermitidos.contains(mimeHeader)
            && mimeTypesPermitidos.contains(mimeReal)
            && extensoesPermitidas.contains(extensao);
    }
}
