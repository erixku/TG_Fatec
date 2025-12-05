package br.app.harppia.modulo.ministry.application.port;

import java.util.UUID;

public interface ConsultarMembroMinstryToUsuarioPort {

	UUID idPorEmail(String email);

}
