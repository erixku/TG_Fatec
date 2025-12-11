package br.app.harppia.modulo.userconfig.application.usecases;

import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.GestaoConfiguracoesAcessibilidadeExcpetion;
import br.app.harppia.modulo.userconfig.domain.request.AtualizarAcessibilidadeAuditivaRequest;
import br.app.harppia.modulo.userconfig.domain.request.AtualizarAcessibilidadeIntelectualRequest;
import br.app.harppia.modulo.userconfig.domain.request.AtualizarAcessibilidadeVisualRequest;
import br.app.harppia.modulo.userconfig.infrastructure.mapper.AcessibilidadeAuditivaMapper;
import br.app.harppia.modulo.userconfig.infrastructure.mapper.AcessibilidadeIntelectualMapper;
import br.app.harppia.modulo.userconfig.infrastructure.mapper.AcessibilidadeVisualMapper;
import br.app.harppia.modulo.userconfig.infrastructure.repository.AcessibilidadeAuditivaRepository;
import br.app.harppia.modulo.userconfig.infrastructure.repository.AcessibilidadeIntelectualRepository;
import br.app.harppia.modulo.userconfig.infrastructure.repository.AcessibilidadeVisualRepository;
import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeAuditivaEntity;
import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeIntelectualEntity;
import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeVisualEntity;

@Service
public class AtualizarConfiguracoesAcessibilidadeUseCase {

	private final AcessibilidadeAuditivaRepository aar;
	private final AcessibilidadeVisualRepository avr;
	private final AcessibilidadeIntelectualRepository air;

	private final AcessibilidadeAuditivaMapper aam;
	private final AcessibilidadeVisualMapper avm;
	private final AcessibilidadeIntelectualMapper aim;

	public AtualizarConfiguracoesAcessibilidadeUseCase(AcessibilidadeAuditivaRepository acbAudRep,
			AcessibilidadeVisualRepository acbVisRep, AcessibilidadeIntelectualRepository acbIntRep,
			AcessibilidadeAuditivaMapper acbAudMpr, AcessibilidadeVisualMapper acbVisMpr,
			AcessibilidadeIntelectualMapper acbIntMpr) {
		this.aar = acbAudRep;
		this.avr = acbVisRep;
		this.air = acbIntRep;
		this.aam = acbAudMpr;
		this.avm = acbVisMpr;
		this.aim = acbIntMpr;
	}

	void todas(AtualizarAcessibilidadeAuditivaRequest atuAcbAudReq, AtualizarAcessibilidadeVisualRequest atuAcbAudVis,
			AtualizarAcessibilidadeIntelectualRequest atuAcbInt) {
		auditiva(atuAcbAudReq);
		intelectual(atuAcbInt);
		visual(atuAcbAudVis);
	}

	void auditiva(AtualizarAcessibilidadeAuditivaRequest atuAcbAudReq) {
		if (atuAcbAudReq == null)
			throw new GestaoConfiguracoesAcessibilidadeExcpetion("Nenhuma configuração auditiva presente!");

		AcessibilidadeAuditivaEntity acbAudEntMapped = aam.toEntity(atuAcbAudReq);

		aar.save(acbAudEntMapped);
	}

	void visual(AtualizarAcessibilidadeVisualRequest atuAcbVisReq) {
		if (atuAcbVisReq == null)
			throw new GestaoConfiguracoesAcessibilidadeExcpetion("Nenhuma configuração visual presente!");

		AcessibilidadeVisualEntity acbAudEntMapped = avm.toEntity(atuAcbVisReq);

		avr.save(acbAudEntMapped);
	}

	void intelectual(AtualizarAcessibilidadeIntelectualRequest atuAcbIntReq) {
		if (atuAcbIntReq == null)
			throw new GestaoConfiguracoesAcessibilidadeExcpetion("Nenhuma configuração intelectual presente!");

		AcessibilidadeIntelectualEntity acbAudEntMapped = aim.toEntity(atuAcbIntReq);

		air.save(acbAudEntMapped);
	}
}
