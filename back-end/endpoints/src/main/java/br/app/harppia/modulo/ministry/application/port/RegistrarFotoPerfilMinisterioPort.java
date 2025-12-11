package br.app.harppia.modulo.ministry.application.port;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.file.domain.valueobjects.FotoPerfilMinisterioRVO;

public interface RegistrarFotoPerfilMinisterioPort {
	
	public FotoPerfilMinisterioRVO proceder(UUID idCriador, MultipartFile mtpFileFoto);
}
