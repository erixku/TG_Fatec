package br.app.harppia.modulo.igreja.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.igreja.domain.request.CadastroIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.response.CadastroIgrejaResponse;
import br.app.harppia.modulo.igreja.infrastructure.mapper.EnderecoIgrejaMapper;
import br.app.harppia.modulo.igreja.infrastructure.mapper.IgrejaMapper;
import br.app.harppia.modulo.igreja.infrastructure.repository.EnderecoIgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.EnderecoIgrejaEntity;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

@Service
public class CriarIgrejaUseCase {

	private final IgrejaRepository igrRep;
	private final EnderecoIgrejaRepository endIgrRep;
	private final IgrejaMapper igrMpr;
	private final EnderecoIgrejaMapper endIgrMpr;

	public CriarIgrejaUseCase(IgrejaRepository igrRep, EnderecoIgrejaRepository endIgrRep, IgrejaMapper igrMpr,
			EnderecoIgrejaMapper endIgrMpr) {
		this.igrRep = igrRep;
		this.endIgrRep = endIgrRep;
		this.igrMpr = igrMpr;
		this.endIgrMpr = endIgrMpr;
	}

	@Transactional
	@UseRole(role = DatabaseRoles.ROLE_OWNER)
	public CadastroIgrejaResponse cadastrar(CadastroIgrejaRequest cadIgrReq) {
		if (cadIgrReq == null)
			throw new GestaoIgrejaException("Falha ao cadastrar igreja: informações ausentes.");

		IgrejaEntity igrEntMapped = igrMpr.toEntity(cadIgrReq);

		IgrejaEntity igrEntSaved = igrRep.save(igrEntMapped);

		EnderecoIgrejaEntity endIgrEntMapped = endIgrMpr.toEntity(cadIgrReq.cadEndIgrReq());
		endIgrEntMapped.setCreatedBy(igrEntSaved.getCreatedBy());
		endIgrEntMapped.setUpdatedBy(igrEntSaved.getCreatedBy());

		EnderecoIgrejaEntity endIgrEntSaved = endIgrRep.save(endIgrEntMapped);

		if (igrEntMapped.getId() == null || endIgrEntSaved.getId() == null)
			throw new GestaoIgrejaException("Houve algum erro ao cadastrar a igreja... Tente novamente mais tarde.");

		return CadastroIgrejaResponse.builder()
				.idIgreja(igrEntSaved.getId())
				.idDono(igrEntSaved.getIdProprietario())
				.nome(igrEntSaved.getNome())
				.build();
	}
}
