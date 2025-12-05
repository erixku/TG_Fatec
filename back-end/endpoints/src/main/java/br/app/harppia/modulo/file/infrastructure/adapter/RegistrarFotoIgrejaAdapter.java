package br.app.harppia.modulo.file.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.modulo.church.application.port.RegistrarFotoPerfilChurchToFilePort;
import br.app.harppia.modulo.church.domain.valueobject.FotoPerfilIgrejaRVO;
import br.app.harppia.modulo.file.application.usecases.SalvarFotoPerfilUseCase;
import br.app.harppia.modulo.file.domain.response.ArquivoPersistidoResponse;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

@Component
public class RegistrarFotoIgrejaAdapter implements RegistrarFotoPerfilChurchToFilePort {

	private final SalvarFotoPerfilUseCase slvFotoPflIgrUC;
	
	private final static ENomeBucket eNomeBkt = ENomeBucket.FOTO_PERFIL_IGREJA; 

	public RegistrarFotoIgrejaAdapter(SalvarFotoPerfilUseCase fileService) {
		this.slvFotoPflIgrUC = fileService;
	}

	@Override
	public FotoPerfilIgrejaRVO proceder(UUID criador, MultipartFile file) {

		if (file == null || file.isEmpty())
			throw new GestaoArquivoException("Nenhum arquivo para fazer o upload!");

		ArquivoPersistidoResponse savedPhoto = slvFotoPflIgrUC.proceder(file, eNomeBkt.getCustomValue(), criador);

		return new FotoPerfilIgrejaRVO(savedPhoto.id(), savedPhoto.link());
	}
}
