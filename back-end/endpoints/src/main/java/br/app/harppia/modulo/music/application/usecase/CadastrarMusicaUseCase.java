package br.app.harppia.modulo.music.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.music.domain.request.CadastroMusicaRequest;
import br.app.harppia.modulo.music.domain.response.CadastroMusicaResponse;
import br.app.harppia.modulo.music.infrastructure.mapper.MusicaMapper;
import br.app.harppia.modulo.music.infrastructure.repository.MusicaRepository;
import br.app.harppia.modulo.music.infrastructure.repository.entities.MusicaEntity;

@Service
public class CadastrarMusicaUseCase {

	private final MusicaRepository musRep;
	private final MusicaMapper musMpr;
	
	public CadastrarMusicaUseCase(MusicaRepository mscRep, MusicaMapper musMpr) {
		this.musRep = mscRep;
		this.musMpr = musMpr;
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_OWNER)
	public CadastroMusicaResponse salvar(CadastroMusicaRequest cadMusReq) {
		if(cadMusReq == null)
			return null;
		
		MusicaEntity musEntSanitized = musMpr.toEntity(cadMusReq);
		
		MusicaEntity musEntSaved = musRep.save(musEntSanitized);
		
		return (musEntSaved.getId() != null) 
				? new CadastroMusicaResponse(musEntSaved.getId())
				: null;
			
	}
	
}
