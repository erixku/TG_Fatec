package br.app.harppia.modulo.file.infrastructure.adapter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.application.usecases.SalvarArquivoUseCase;
import br.app.harppia.modulo.file.domain.valueobjects.ArquivoPersistidoResponse;
import br.app.harppia.modulo.usuario.application.port.out.RegistrarArquivoPort;
import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilInfo;

@Component
public class RegistrarArquivoAdapter implements RegistrarArquivoPort {

	private final SalvarArquivoUseCase fileService;

	public RegistrarArquivoAdapter(SalvarArquivoUseCase fileService) {
		this.fileService = fileService;
	}

	@Override
	public FotoPerfilInfo registrarFotoPerfilUsuario(MultipartFile file, String pasta) {

		if (file == null || file.isEmpty())
			return null;

		if (pasta == null || pasta.trim().isEmpty())
			return null;

		try {
			ArquivoPersistidoResponse savedPhoto = fileService.salvar(file, pasta);
			return new FotoPerfilInfo(savedPhoto.id(), savedPhoto.link());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
