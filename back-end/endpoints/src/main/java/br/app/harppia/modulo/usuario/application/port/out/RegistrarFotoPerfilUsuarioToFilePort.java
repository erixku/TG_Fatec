package br.app.harppia.modulo.usuario.application.port.out;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilUsuarioRVO;

public interface RegistrarFotoPerfilUsuarioToFilePort {
	FotoPerfilUsuarioRVO persistir(MultipartFile file, UUID criador);
}

