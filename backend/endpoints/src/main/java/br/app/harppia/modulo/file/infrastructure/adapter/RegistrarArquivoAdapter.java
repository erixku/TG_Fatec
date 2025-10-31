package br.app.harppia.modulo.file.infrastructure.adapter;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.ArquivoInvalidoException;
import br.app.harppia.defaults.custom.exceptions.RegistrarArquivoException;
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
	public FotoPerfilInfo registrarFotoPerfilUsuario(MultipartFile file, String pasta, UUID criador) {

		if (file == null || file.isEmpty())
			return null;

		if (pasta == null || pasta.trim().isEmpty())
			return null;

		try {		
			ArquivoPersistidoResponse savedPhoto = fileService.salvar(file, pasta, criador);
			
			return new FotoPerfilInfo(savedPhoto.id(), savedPhoto.link());
		} catch (IOException e) {
			System.err.println("Houve algum erro de I/O.");
			e.printStackTrace();
		} catch (RegistrarArquivoException | ArquivoInvalidoException e) {
			System.err.println("Houve algum erro durante o cadastro do arquivo.");
			e.printStackTrace();
		}
		
		
		return null;
	}
}
