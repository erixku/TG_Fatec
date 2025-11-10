package br.app.harppia.modulo.file.application.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EExtensaoArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.EMimeTypeArquivo;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

public class FileValidatorService {

	private static final Tika tika = new Tika();

	private ENomeBucket nomeBkt;
	private Integer tamMax;
	private Integer tamMin;
	private boolean isBktDlt;

	public FileValidatorService(BucketRestricoesUploadInfo bucketContraints) {
		this.nomeBkt = bucketContraints.nome();
		this.tamMax = bucketContraints.tamanhoMaximo();
		this.tamMin = bucketContraints.tamanhoMinimo();
		this.isBktDlt = bucketContraints.isDeleted();
	}

	public boolean arquivoEstaValido(MultipartFile file) {

		if (isBktDlt)
			throw new GestaoArquivoException("O bucket " + nomeBkt.getCustomValue() + " não está mais disponível.");

		String realMimeType = detectRealMimeType(file);
		if (!EMimeTypeArquivo.contains(realMimeType))
			throw new GestaoArquivoException("Tipo MIME do arquivo não permitido: " + realMimeType);

		String declaredMime = file.getContentType();
		if (declaredMime != null && !realMimeType.equalsIgnoreCase(declaredMime))
			throw new GestaoArquivoException("Tipo MIME inconsistente entre o detectado e o declarado!");

		if (!EExtensaoArquivo.contains(pegarExtensao(file.getOriginalFilename())))
			throw new GestaoArquivoException("Extensão do arquivo inválida!");

		if (file.getSize() < tamMin || file.getSize() > tamMax)
			throw new GestaoArquivoException(String.format("Tamanho inválido! (%d bytes, permitido entre %d e %d)",
					file.getSize(), tamMin, tamMax));

		return true;
	}

	public static String detectRealMimeType(MultipartFile file) {
		if (file == null || file.isEmpty())
			throw new GestaoArquivoException("Arquivo inválido ou vazio!");

		try (InputStream is = file.getInputStream()) {
			return tika.detect(is);
		} catch (IOException ex) {
			throw new GestaoArquivoException(
					"Não foi possível detectar o tipo MIME do arquivo: " + file.getOriginalFilename());
		}
	}

	public String pegarExtensao(String nomeArquivoCompleto) {

		if (nomeArquivoCompleto == null || !nomeArquivoCompleto.contains("."))
			return null;

		int lastDotIndex = nomeArquivoCompleto.lastIndexOf('.');

		return (lastDotIndex > 0 && lastDotIndex < nomeArquivoCompleto.length() - 1)
				? nomeArquivoCompleto.substring(lastDotIndex + 1).toLowerCase()
				: null;
	}
}
