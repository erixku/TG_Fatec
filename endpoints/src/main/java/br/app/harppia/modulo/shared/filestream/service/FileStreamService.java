package br.app.harppia.modulo.shared.filestream.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.S3Template;

/**
 * Essa classe é responsável por gerir as requisições de escrita e leitura de
 * arquivos salvos em hospedagens externas.
 */
@Service
public class FileStreamService {

	private final S3Template s3Template;
	private final String bucketName;
	private final String region;

	public FileStreamService(S3Template s3Template, @Value("${application.aws.s3.bucketname}") String bucketName,
			@Value("${spring.cloud.aws.region.static}") String region) {
		this.s3Template = s3Template;
		this.bucketName = bucketName;
		this.region = region;
	}

	/**
	 * Envia o arquivo para a hospedagem na cloud definida na classe (S3).
	 * 
	 * @param file         o arquivo a ser submetido
	 * @param folderToSave o diretório de destino. Ex: "nomedapasta" (sem barras)
	 * @return
	 * @throws IOException
	 */
	public String uploadFile(MultipartFile file, String folderToSave) throws IOException {

		if (file.isEmpty() || file == null)
			throw new IllegalArgumentException("Falha ao enviar arquivo: arquivo ausente.");

		// Gera um nome de arquivo único para evitar colisões
		String key = "";

		if (!folderToSave.trim().isEmpty() || folderToSave != null)
			key.concat(folderToSave).concat("/");

		key.concat(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());

		// Faz o upload e retorna a URL pública do objeto
		s3Template.upload(bucketName, key, file.getInputStream());

		// Retorna a URL do arquivo no S3
		return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
	}
}
