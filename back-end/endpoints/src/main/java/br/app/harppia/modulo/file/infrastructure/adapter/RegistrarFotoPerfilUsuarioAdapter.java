package br.app.harppia.modulo.file.infrastructure.adapter;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.modulo.file.application.usecases.SalvarFotoPerfilUseCase;
import br.app.harppia.modulo.file.domain.response.ArquivoPersistidoResponse;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import br.app.harppia.modulo.usuario.application.port.out.RegistrarFotoPerfilUsuarioPort;
import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilUsuarioRVO;

@Component
public class RegistrarFotoPerfilUsuarioAdapter implements RegistrarFotoPerfilUsuarioPort {

	private final SalvarFotoPerfilUseCase slvFotoPrfUC;
	
	private final static ENomeBucket PASTA_DESTINO = ENomeBucket.FOTO_PERFIL_USUARIO;

	public RegistrarFotoPerfilUsuarioAdapter(SalvarFotoPerfilUseCase slvFotoPrfUC) {
		this.slvFotoPrfUC = slvFotoPrfUC;
	}

	@Override
	public FotoPerfilUsuarioRVO persistir(MultipartFile mtpFileFoto, UUID idCriador) {

		if (mtpFileFoto == null || mtpFileFoto.isEmpty())
			throw new GestaoArquivoException("Nenhum arquivo para fazer o upload!");

		if (idCriador == null)
			throw new GestaoArquivoException("É necessário a identificação do criador!");
					
		ArquivoPersistidoResponse savedPhoto = slvFotoPrfUC.proceder(mtpFileFoto, PASTA_DESTINO.getCustomValue(), idCriador);

		return new FotoPerfilUsuarioRVO(savedPhoto.id(), savedPhoto.link());
	}
}
