package br.app.harppia.modulo.church.application.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.church.domain.request.BuscarIgrejaRequest;
import br.app.harppia.modulo.church.domain.response.BuscarIgrejaResponse;
import br.app.harppia.modulo.church.domain.response.BuscarListaIdsIgrejasResponse;
import br.app.harppia.modulo.church.domain.response.BuscarListaIgrejasResponse;
import br.app.harppia.modulo.church.domain.valueobject.InformacaoIgrejaRVO;
import br.app.harppia.modulo.church.domain.valueobject.RoleMembroMinisterioRVO;
import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import br.app.harppia.modulo.church.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.church.infrastructure.repository.entities.IgrejaEntity;
import br.app.harppia.modulo.church.infrastructure.repository.projection.AllRolesMembroIVO;
import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;

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

		InformacaoIgrejaRVO infIgrRVO = InformacaoIgrejaRVO.builder().id(idIgreja).cnpj(igrEntFound.get().getCnpj())
				.nome(igrEntFound.get().getNome()).denominacao(igrEntFound.get().getDenominacao())
				.outraDenominacao(igrEntFound.get().getOutraDenominacao()).build();

		return BuscarIgrejaResponse.builder().infIgrRVO(infIgrRVO).build();
	}

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_OWNER)
	public BuscarListaIdsIgrejasResponse listaIgrejasVinculadasAoUsuario(UUID idUsuario) {
		if (idUsuario == null)
			throw new GestaoIgrejaException("Não foi possível buscar pela igreja: dados ausentes!");

		List<UUID> igrejas = igrRep.findIdIgrejasByAssociationWithUser(idUsuario);

		return new BuscarListaIdsIgrejasResponse(igrejas);
	}

	public List<RolesMembroPorIgrejaMinisterioRVO> listaRolesMembro(UUID id) {

	    List<AllRolesMembroIVO> lstAllRolMemIVOBanco = igrRep.findRolesMembroById(id);

	    if (lstAllRolMemIVOBanco == null || lstAllRolMemIVOBanco.isEmpty())
	        return null;

	    // Agrupa as linhas do banco pelo ID da Igreja
	    Map<UUID, List<AllRolesMembroIVO>> mapPorIgreja = lstAllRolMemIVOBanco.stream()
	            .collect(Collectors.groupingBy(AllRolesMembroIVO::getIgreja));

	    List<RolesMembroPorIgrejaMinisterioRVO> lstResponse = new ArrayList<>();

	    for (Map.Entry<UUID, List<AllRolesMembroIVO>> entry : mapPorIgreja.entrySet()) {
	        UUID idIgreja = entry.getKey();
	        List<AllRolesMembroIVO> dadosDaIgreja = entry.getValue();

	        Set<String> rolesIgrejaSet = dadosDaIgreja.stream()
	                .map(AllRolesMembroIVO::getRoleUsuarioIgreja)
	                .filter(Objects::nonNull)
	                .collect(Collectors.toSet());

	        List<RoleMembroMinisterioRVO> rolesMinisterioList = dadosDaIgreja.stream()
	                .filter(row -> row.getMinisterio() != null && row.getFuncao() != null)
	                .map(row -> new RoleMembroMinisterioRVO(
	                        row.getMinisterio(),
	                        EFuncaoMembro.fromValue(row.getFuncao())
	                ))
	                .collect(Collectors.toList());

	        lstResponse.add(RolesMembroPorIgrejaMinisterioRVO.builder()
	                .idIgreja(idIgreja)
	                .cargosNaIgreja(new ArrayList<>(rolesIgrejaSet))
	                .funcaoPorMinisterio(rolesMinisterioList)
	                .build()
	        );
	    }

	    return lstResponse.isEmpty() ? null : lstResponse;
	}
}
