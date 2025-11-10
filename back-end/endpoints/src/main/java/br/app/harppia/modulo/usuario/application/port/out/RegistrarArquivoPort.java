package br.app.harppia.modulo.usuario.application.port.out;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.modulo.usuario.domain.dto.FotoPerfilInfo;

public interface RegistrarArquivoPort {
	FotoPerfilInfo registrarFotoPerfilUsuario(MultipartFile file, String pasta, UUID criador);
}

