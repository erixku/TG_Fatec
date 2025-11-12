package br.app.harppia.modulo.userconfig.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoConfiguracoesAcessibilidadeExcpetion;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeAuditivaRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeIntelectualRequest;
import br.app.harppia.modulo.userconfig.domain.request.SalvarAcessibilidadeVisualRequest;
import br.app.harppia.modulo.userconfig.infrastructure.repository.AcessibilidadeAuditivaRepository;
import br.app.harppia.modulo.userconfig.infrastructure.repository.AcessibilidadeIntelectualRepository;
import br.app.harppia.modulo.userconfig.infrastructure.repository.AcessibilidadeVisualRepository;

@Service
public class SalvarConfiguracoesAcessibilidadeUseCase {

	private final AcessibilidadeAuditivaRepository aar;
	private final AcessibilidadeVisualRepository avr;
	private final AcessibilidadeIntelectualRepository air;

	public SalvarConfiguracoesAcessibilidadeUseCase(AcessibilidadeAuditivaRepository acbAudRep,
			AcessibilidadeVisualRepository acbVisRep, AcessibilidadeIntelectualRepository acbIntRep) {
		this.aar = acbAudRep;
		this.avr = acbVisRep;
		this.air = acbIntRep;
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public void todas(SalvarAcessibilidadeAuditivaRequest slvAcbAudReq, SalvarAcessibilidadeVisualRequest slvAcbAudVis,
			SalvarAcessibilidadeIntelectualRequest slvAcbInt) {
		auditiva(slvAcbAudReq);
		intelectual(slvAcbInt);
		visual(slvAcbAudVis);
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public void auditiva(SalvarAcessibilidadeAuditivaRequest slvAcbAudReq) {
		if (slvAcbAudReq == null)
			throw new GestaoConfiguracoesAcessibilidadeExcpetion("Nenhuma configuração auditiva presente!");
		
		aar.firstSave(slvAcbAudReq.idDonoConfig());
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public void visual(SalvarAcessibilidadeVisualRequest slvAcbVisReq) {
		if (slvAcbVisReq == null)
			throw new GestaoConfiguracoesAcessibilidadeExcpetion("Nenhuma configuração visual presente!");

		avr.firstSave(slvAcbVisReq.idDonoConfig());
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_ANONIMO)
	public void intelectual(SalvarAcessibilidadeIntelectualRequest slvAcbIntReq) {
		if (slvAcbIntReq == null)
			throw new GestaoConfiguracoesAcessibilidadeExcpetion("Nenhuma configuração intelectual presente!");

		air.firstSave(slvAcbIntReq.idDonoConfig());
	}
}
