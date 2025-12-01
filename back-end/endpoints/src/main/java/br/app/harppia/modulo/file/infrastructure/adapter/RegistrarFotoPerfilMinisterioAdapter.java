package br.app.harppia.modulo.file.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.modulo.file.application.usecases.SalvarFotoPerfilUseCase;
import br.app.harppia.modulo.file.domain.response.ArquivoPersistidoResponse;
import br.app.harppia.modulo.file.domain.valueobjects.FotoPerfilMinisterioRVO;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import br.app.harppia.modulo.ministry.application.port.RegistrarFotoPerfilMinisterioPort;

@Component
public class RegistrarFotoPerfilMinisterioAdapter implements RegistrarFotoPerfilMinisterioPort {

	private final SalvarFotoPerfilUseCase slvFotoPrfUC;
	
	private final static ENomeBucket PASTA_DESTINO = ENomeBucket.FOTO_PERFIL_MINISTERIO;

	public RegistrarFotoPerfilMinisterioAdapter(SalvarFotoPerfilUseCase slvFotoPrfUC) {
		this.slvFotoPrfUC = slvFotoPrfUC;
	}

	@Override
	public FotoPerfilMinisterioRVO proceder(UUID idCriador, MultipartFile mtpFileFoto) {

		if (mtpFileFoto == null || mtpFileFoto.isEmpty())
			throw new GestaoArquivoException("Nenhum arquivo para fazer o upload!");

		if (idCriador == null)
			throw new GestaoArquivoException("É necessário a identificação do criador!");
					
		ArquivoPersistidoResponse savedPhoto = slvFotoPrfUC.proceder(mtpFileFoto, PASTA_DESTINO.getCustomValue(), idCriador);

		return new FotoPerfilMinisterioRVO(savedPhoto.id(), savedPhoto.link());
	}
}
