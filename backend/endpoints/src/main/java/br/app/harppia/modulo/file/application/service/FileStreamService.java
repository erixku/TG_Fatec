package br.app.harppia.modulo.file.application.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.RegistrarArquivoException;
import br.app.harppia.modulo.file.domain.entities.Arquivo;
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
	 * Envia o arquivo para a hospedagem na cloud definida na classe.<br>
	 * Hospedagem definida: Amazon S3
	 * 
	 * @param file         o arquivo a ser submetido
	 * @param folderToSave o diretório de destino. Ex: "nomedapasta" (sem barras)
	 * @return
	 * @throws IOException
	 * @throws RegistrarArquivoException
	 */
	public Arquivo uploadFile(MultipartFile file, String folderToSave, UUID criador) throws IOException {

		if (file == null || file.isEmpty())
			throw new RegistrarArquivoException("Falha ao enviar arquivo: arquivo ausente.");

		String keyName = "";

		if (folderToSave == null || folderToSave.trim().isEmpty())
			throw new RegistrarArquivoException("Falha ao enviar arquivo: diretorio ausente.");

		String[] nomeArquivo = file.getOriginalFilename().split("/");
		String nomeNormalizado;

		nomeNormalizado = normalizarNomeArquivo(
				(nomeArquivo.length > 1) 
					? nomeArquivo[nomeArquivo.length - 1] 
					: nomeArquivo[0]
			);

		keyName = keyName.concat(folderToSave).concat("/");
		keyName = keyName.concat(criador.toString()).concat("_");
		keyName = keyName.concat(nomeNormalizado);

		s3Template.upload(bucketName, keyName, file.getInputStream());

		return new Arquivo(file, String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, keyName),
				nomeNormalizado.replaceFirst("\\.[^.]+$", ""));
	}

	private String normalizarNomeArquivo(String nome) {

	    if (nome == null) return null;
	    
	    String nomeLimpo = nome.replaceAll("[^a-zA-Z0-9\\-_. ]", "");

	    return nomeLimpo
	        .replaceAll("\\s+", " ")
	        .trim()
	        .toLowerCase();
	}
}
