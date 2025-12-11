package br.app.harppia.modulo.activities.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.activities.domain.request.BuscarPublicacoesRequest;
import br.app.harppia.modulo.activities.domain.response.BuscarPublicacoesResponse;
import br.app.harppia.modulo.activities.infrastructure.projection.DadosPublicacaoIVO;
import br.app.harppia.modulo.activities.infrastructure.repository.PublicacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BuscarPublicacoesUseCase {

	private final PublicacaoRepository pubRep;

	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public BuscarPublicacoesResponse listFromChurch(UUID idIgreja, BuscarPublicacoesRequest busPubReqParametros) {

		if (idIgreja == null)
			return null;

		List<DadosPublicacaoIVO> lstDadPubIVO = pubRep.getAllByIdIgreja(idIgreja);

		return (!lstDadPubIVO.isEmpty()) ? new BuscarPublicacoesResponse(lstDadPubIVO) : null;
	}

	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public BuscarPublicacoesResponse listFromMinistry(UUID idIgreja, UUID idMinisterio, BuscarPublicacoesRequest busPubReqParametros) {
		if (idMinisterio == null)
			return null;
		
		List<DadosPublicacaoIVO> lstDadPubIVO = pubRep.getAllByIdIgrejaAndIdMinisterio(idIgreja, idMinisterio);
		
		return (!lstDadPubIVO.isEmpty()) ? new BuscarPublicacoesResponse(lstDadPubIVO) : null;
	}
}
