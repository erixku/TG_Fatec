package br.app.harppia.modulo.file.application.service;

import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.ArquivoInvalidoException;
import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;


public class FileValidatorService {
	
    private static final Tika tika = new Tika();
    
    private ENomeBucket bucketName;
    private Long tamanhoMaximo;
    private Long tamanhoMinimo;
    private boolean isDeleted;
	
    public FileValidatorService(BucketRestricoesUploadInfo bucketContraints) {
    	this.bucketName = bucketContraints.nome();
    	this.tamanhoMaximo = bucketContraints.tamanhoMaximo();
    	this.tamanhoMinimo = bucketContraints.tamanhoMinimo();
    	this.isDeleted = bucketContraints.isDeleted();
    }
	
	public static String detectRealMimeType(MultipartFile file) throws IOException {
	    return tika.detect(file.getInputStream());
	}
	
	public boolean arquivoEstaValido(MultipartFile file) throws IOException, ArquivoInvalidoException {
		
		if(file == null || file.isEmpty())
			throw new ArquivoInvalidoException("Arquivo inválido ou ausente!");
		
		if(isDeleted)
			throw new ArquivoInvalidoException("O bucket " + bucketName.getCustomValue() + " não está mais disponível.");
		
		if(!EMimeTypeArquivo.contains(file.getContentType())) 
			throw new ArquivoInvalidoException("Mime type do arquivo inválido!");
		
		String realMimeType = FileValidatorService.detectRealMimeType(file);
		if(!realMimeType.equalsIgnoreCase(file.getContentType()))
			throw new ArquivoInvalidoException("Mime type inconsistente!");
		
		if(!EExtensaoArquivo.contains(pegarExtensao(file.getOriginalFilename())))
			throw new ArquivoInvalidoException("Extensão do arquivo inválida!");
		
		if(file.getSize() < tamanhoMinimo || file.getSize() > tamanhoMaximo)
			throw new ArquivoInvalidoException("O arquivo é muito pequeno/grande!");
		
		return true;
	}

	public String pegarExtensao(String nomeArquivoCompleto) {

		if (nomeArquivoCompleto == null)
			return null;

		int lastDotIndex = nomeArquivoCompleto.lastIndexOf('.');

		String extensao = (lastDotIndex > 0) ? nomeArquivoCompleto.substring(lastDotIndex + 1) : null;
		
		return (extensao != null) ? extensao : null;
	}
}
