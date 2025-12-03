package br.app.harppia.modulo.igreja.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.igreja.domain.request.BuscarIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.response.BuscarIgrejaResponse;
import br.app.harppia.modulo.igreja.domain.response.BuscarListaIdsIgrejasResponse;
import br.app.harppia.modulo.igreja.domain.response.BuscarListaIgrejasResponse;
import br.app.harppia.modulo.igreja.domain.valueobject.InformacaoIgrejaRVO;
import br.app.harppia.modulo.igreja.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

@Service
public class BuscarIgrejaUseCase {

	private final IgrejaRepository igrRep;

	public BuscarIgrejaUseCase(IgrejaRepository igrRep) {
		this.igrRep = igrRep;
	}

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public BuscarListaIgrejasResponse listaContendoNome(BuscarIgrejaRequest requestDto) {

		if (requestDto == null)
			throw new GestaoIgrejaException("Não foi possível buscar pela igreja: dados ausentes!");

		List<InformacaoIgrejaRVO> igrejas = igrRep.findIgrejasByNomeContainingIgnoreCase(requestDto.nome());

		return BuscarListaIgrejasResponse.builder().igrejas(igrejas).build();
	}

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public BuscarIgrejaResponse porId(UUID idIgreja) {

		if (idIgreja == null)
			throw new GestaoIgrejaException("Não foi possível buscar pela igreja: dados ausentes!");
		
		Optional<IgrejaEntity> igrEntFound = igrRep.findById(idIgreja);

		if (igrEntFound.isEmpty())
			return null;

		InformacaoIgrejaRVO infIgrRVO = InformacaoIgrejaRVO.builder()
				.id(idIgreja)
				.cnpj(igrEntFound.get().getCnpj())
				.nome(igrEntFound.get().getNome())
				.denominacao(igrEntFound.get().getDenominacao())
				.outraDenominacao(igrEntFound.get().getOutraDenominacao())
				.build();

		return BuscarIgrejaResponse.builder()
				.infIgrRVO(infIgrRVO)
				.build();
	}
	
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public BuscarListaIdsIgrejasResponse listaIgrejasVinculadasAoUsuario(UUID idUsuario) {
		if(idUsuario == null)
			throw new GestaoIgrejaException("Não foi possível buscar pela igreja: dados ausentes!");
		
		List<UUID> igrejas = igrRep.findIdIgrejasByAssociationWithUser(idUsuario);
		
		return new BuscarListaIdsIgrejasResponse(igrejas); 
		
	}
}
