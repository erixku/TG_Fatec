package br.app.harppia.modulo.file.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.modulo.file.application.usecases.SalvarFotoPerfilUseCase;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoPersistidoResponse;
import br.app.harppia.modulo.usuario.application.port.out.RegistrarArquivoPort;
import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilInfo;

@Component
public class RegistrarArquivoAdapter implements RegistrarArquivoPort {

	private final SalvarFotoPerfilUseCase fileService;

	public RegistrarArquivoAdapter(SalvarFotoPerfilUseCase fileService) {
		this.fileService = fileService;
	}

	@Override
	public FotoPerfilInfo registrarFotoPerfilUsuario(MultipartFile file, String pasta, UUID criador) {

		if (file == null || file.isEmpty())
			throw new GestaoArquivoException("Nenhum arquivo para fazer o upload!");

		if (pasta == null || pasta.trim().isEmpty())
			throw new GestaoArquivoException("É necessário especificar a pasta para salvar o arquivo!");

		ArquivoPersistidoResponse savedPhoto = fileService.salvar(file, pasta, criador);

		return new FotoPerfilInfo(savedPhoto.id(), savedPhoto.link());
	}
}
