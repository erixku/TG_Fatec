package br.app.harppia.modulo.igreja.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.igreja.application.port.RegistrarFotoPerfilIgrejaPort;
import br.app.harppia.modulo.igreja.domain.request.CadastroIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.response.CadastroIgrejaResponse;
import br.app.harppia.modulo.igreja.domain.valueobject.FotoPerfilIgrejaRVO;
import br.app.harppia.modulo.igreja.infrastructure.mapper.EnderecoIgrejaMapper;
import br.app.harppia.modulo.igreja.infrastructure.mapper.IgrejaMapper;
import br.app.harppia.modulo.igreja.infrastructure.repository.EnderecoIgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.EnderecoIgrejaEntity;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;
import br.app.harppia.modulo.igreja.infrastructure.repository.enums.EDenominacaoIgreja;

@Service
public class CriarIgrejaUseCase {

	private final IgrejaRepository igrRep;
	private final EnderecoIgrejaRepository endIgrRep;

	private final IgrejaMapper igrMpr;
	private final EnderecoIgrejaMapper endIgrMpr;

	private final RegistrarFotoPerfilIgrejaPort rgtFotoPrfIgrPort;

	private final static UUID ID_FOTO_PADRAO = UUID.fromString("4633f137-3d8a-43d2-90f2-2bfb53b4e8fc");

	public CriarIgrejaUseCase(IgrejaRepository igrRep, EnderecoIgrejaRepository endIgrRep, IgrejaMapper igrMpr,
			EnderecoIgrejaMapper endIgrMpr, RegistrarFotoPerfilIgrejaPort rgtFotoPrfIgrPort) {
		this.igrRep = igrRep;
		this.endIgrRep = endIgrRep;
		this.igrMpr = igrMpr;
		this.endIgrMpr = endIgrMpr;
		this.rgtFotoPrfIgrPort = rgtFotoPrfIgrPort;
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_OWNER)
	public CadastroIgrejaResponse proceder(CadastroIgrejaRequest cadIgrReq, MultipartFile mtpFileFoto) {
		
		if (cadIgrReq == null)
			throw new GestaoIgrejaException("Falha ao cadastrar igreja: informações ausentes.");

		IgrejaEntity igrEntMapped = igrMpr.toEntity(cadIgrReq);
		
		if(igrEntMapped.getDenominacao().equals(EDenominacaoIgreja.OUTRA))
			igrEntMapped.setOutraDenominacao(cadIgrReq.outraDenominacao());

		if (mtpFileFoto == null || mtpFileFoto.isEmpty()) {
			igrEntMapped.setIdFoto(ID_FOTO_PADRAO);
		} else {
			FotoPerfilIgrejaRVO fotoPerfil = rgtFotoPrfIgrPort.proceder(cadIgrReq.idCriador(), mtpFileFoto);
			igrEntMapped.setIdFoto(fotoPerfil.id());
		}

		IgrejaEntity igrEntSaved = igrRep.save(igrEntMapped);

		EnderecoIgrejaEntity endIgrEntMapped = endIgrMpr.toEntity(cadIgrReq.cadEndIgrReq());
		endIgrEntMapped.setIgreja(igrEntSaved);
		endIgrEntMapped.setCreatedBy(igrEntSaved.getCreatedBy());
		endIgrEntMapped.setUpdatedBy(igrEntSaved.getCreatedBy());
		
		EnderecoIgrejaEntity endIgrEntSaved = endIgrRep.save(endIgrEntMapped);

		if (igrEntMapped.getId() == null || endIgrEntSaved.getId() == null)
			throw new GestaoIgrejaException("Houve algum erro ao cadastrar a igreja... Tente novamente mais tarde.");
		
		return CadastroIgrejaResponse.builder().idIgreja(igrEntSaved.getId()).idDono(igrEntSaved.getIdProprietario())
				.nome(igrEntSaved.getNome()).build();
	}
}
