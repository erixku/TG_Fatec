package br.app.harppia.modulo.igreja.application.port;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.igreja.domain.valueobject.FotoPerfilIgrejaRVO;

public interface RegistrarFotoPerfilIgrejaPort {
	
	public FotoPerfilIgrejaRVO proceder(UUID idCriador, MultipartFile mptFileArquivo);

}
