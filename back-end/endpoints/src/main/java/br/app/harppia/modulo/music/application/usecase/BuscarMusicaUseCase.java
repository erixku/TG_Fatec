package br.app.harppia.modulo.music.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoMusicaException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.music.domain.request.BuscarMusicaRequest;
import br.app.harppia.modulo.music.domain.response.BuscarMusicaResponse;
import br.app.harppia.modulo.music.domain.valueobject.InformacoesBasicasMusicaRVO;
import br.app.harppia.modulo.music.infrastructure.repository.MusicaRepository;

@Service
public class BuscarMusicaUseCase {

	private final MusicaRepository musRep;
	
	public BuscarMusicaUseCase(MusicaRepository mscRep) {
		this.musRep = mscRep;
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_OWNER)
	public BuscarMusicaResponse buscar(BuscarMusicaRequest busMusReq) {
		if (busMusReq == null)
			throw new GestaoMusicaException("Nenhuma informação base para buscar a(s) música(s)!");

		List<InformacoesBasicasMusicaRVO> listInfBscMusRVO = musRep.findMusicasByNomeContainingIgnoreCase(busMusReq.nome());
		
		return (listInfBscMusRVO != null && !listInfBscMusRVO.isEmpty()) 
				? new BuscarMusicaResponse(listInfBscMusRVO)
				: null;
	}
}