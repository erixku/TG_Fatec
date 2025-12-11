package br.app.harppia.modulo.activities.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.activities.application.port.out.ConsultarIgrejaActivityToChurchPort;
import br.app.harppia.modulo.activities.domain.request.CriarAtividadeRequest;
import br.app.harppia.modulo.activities.infrastructure.mapper.AtividadeMapper;
import br.app.harppia.modulo.activities.infrastructure.mapper.PublicacaoMapper;
import br.app.harppia.modulo.activities.infrastructure.repository.AtividadeRepository;
import br.app.harppia.modulo.activities.infrastructure.repository.PublicacaoRepository;
import br.app.harppia.modulo.activities.infrastructure.repository.entities.AtividadeEntity;
import br.app.harppia.modulo.activities.infrastructure.repository.entities.PublicacaoEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CriarAtividadeUseCase {

	private final ConsultarIgrejaActivityToChurchPort conIgrActToChrPort;
	
	private final PublicacaoRepository pubRep;
	private final PublicacaoMapper pubMpr;

	private final AtividadeRepository atvRep;
	private final AtividadeMapper atvMpr;

	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public boolean proceder(CriarAtividadeRequest criAtvReq) {
		
		PublicacaoEntity pubEntMapped = pubMpr.toEntity(criAtvReq);
		pubEntMapped = pubRep.save(pubEntMapped);

		Integer intIdCategoria = conIgrActToChrPort.getIdOf(criAtvReq.eTipoAtividade());
		
		AtividadeEntity atvEntPopulated = atvMpr.toEntity(criAtvReq.periodo(), pubEntMapped.getId(), intIdCategoria);
		atvEntPopulated.setPeriodo("[2025-12-14 19:00:00-03, 2025-12-14 21:00:00-03)");
		atvEntPopulated = atvRep.save(atvEntPopulated);
		
		return true;
	}
}
