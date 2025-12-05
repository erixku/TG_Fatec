package br.app.harppia.modulo.church.application.port;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.church.domain.valueobject.FotoPerfilIgrejaRVO;

public interface RegistrarFotoPerfilChurchToFilePort {
	
	public FotoPerfilIgrejaRVO proceder(UUID idCriador, MultipartFile mptFileArquivo);

}
