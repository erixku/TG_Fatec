package br.app.harppia.modulo.ministry.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.exceptions.GestaoMinisterioException;
import br.app.harppia.modulo.church.domain.valueobject.InformacaoIgrejaRVO;
import br.app.harppia.modulo.file.domain.valueobjects.FotoPerfilMinisterioRVO;
import br.app.harppia.modulo.ministry.application.port.ConsultarIgrejaMinistryToChurchPort;
import br.app.harppia.modulo.ministry.application.port.RegistrarFotoPerfilMinisterioPort;
import br.app.harppia.modulo.ministry.domain.request.CriarMinisterioRequest;
import br.app.harppia.modulo.ministry.domain.response.CriarMinisterioResponse;
import br.app.harppia.modulo.ministry.infraestructure.mapper.MinisterioMapper;
import br.app.harppia.modulo.ministry.infraestructure.repository.MinisterioRepository;
import br.app.harppia.modulo.ministry.infraestructure.repository.entities.MinisterioEntity;

@Service
public class CriarMinisterioUseCase {

	private final MinisterioRepository minRep;

	private final MinisterioMapper minMpr;

	private final RegistrarFotoPerfilMinisterioPort regFotoPrfMinPort;
	private final ConsultarIgrejaMinistryToChurchPort conIgrMinPort;

	private final static UUID ID_FOTO_PADRAO = UUID.fromString("4633f137-3d8a-43d2-90f2-2bfb53b4e8fc");

	public CriarMinisterioUseCase(MinisterioRepository minRep, MinisterioMapper minMpr,
			RegistrarFotoPerfilMinisterioPort regFotoPrfMinPort, ConsultarIgrejaMinistryToChurchPort conIgrPort) {
		this.minRep = minRep;
		this.minMpr = minMpr;
		this.regFotoPrfMinPort = regFotoPrfMinPort;
		this.conIgrMinPort = conIgrPort;
	}

	public CriarMinisterioResponse proceder(CriarMinisterioRequest criMinReq, MultipartFile mtpFile) {

		if (criMinReq == null)
			throw new GestaoMinisterioException("Falha ao cadastrar ministério: dados ausentes!");

		MinisterioEntity minEntMapped = minMpr.toEntity(criMinReq);

		InformacaoIgrejaRVO infIgrRVO = conIgrMinPort.porId(criMinReq.idIgreja());
		if (infIgrRVO == null || infIgrRVO.id() == null)
			throw new GestaoMinisterioException("Erro ao cadastrar ministério: igreja inexistente!");

		minEntMapped.setIdIgreja(infIgrRVO.id());

		if (mtpFile == null || mtpFile.isEmpty()) {
			minEntMapped.setIdFoto(ID_FOTO_PADRAO);
		} else {
			FotoPerfilMinisterioRVO fotoPrfMinRVO = regFotoPrfMinPort.proceder(criMinReq.idCriador(), mtpFile);
			minEntMapped.setIdFoto(fotoPrfMinRVO.idFoto());
		}

		MinisterioEntity minEntSaved;

		try {
			
			minEntSaved = minRep.save(minEntMapped);
			
		} catch (Exception sqlEx) {

			throw new GestaoMinisterioException(
					"Não foi possível cadastrar o ministério. "
					+ "Talvez já exista outro ministério com mesmo nome em sua igreja...");
		}

		CriarMinisterioResponse criMinRes = CriarMinisterioResponse.builder().idMinisterio(minEntSaved.getId())
				.nome(minEntSaved.getNome()).descricao(minEntSaved.getDescricao()).build();

		return criMinRes;
	}

}
