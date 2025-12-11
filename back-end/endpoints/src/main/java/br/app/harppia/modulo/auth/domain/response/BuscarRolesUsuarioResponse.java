package br.app.harppia.modulo.auth.domain.response;

import java.util.List;
import java.util.UUID;

import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;

public class BuscarRolesUsuarioResponse {
	UUID idUsuario;
	List<IgrejaVinculadaUsuario> igrejasFazParte;
}

class IgrejaVinculadaUsuario {
	UUID idIgreja;
	ERoleUsuarioIgreja roleUsuarioIgreja;
	List<Ministerio> ministerios;
	
}

class Ministerio {
	UUID idMinisterio;
	List<Membro> membrosMinisterio;
}

class Membro {
	UUID idMembro;
	List<EFuncaoMembro> funcaoMembro;
}

enum ERoleUsuarioIgreja{
	CRIADOR,
	ADMINISTRADOR,
	MEMBRO;
}