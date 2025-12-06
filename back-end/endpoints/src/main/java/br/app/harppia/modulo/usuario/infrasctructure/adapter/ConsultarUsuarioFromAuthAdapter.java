package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.auth.domain.valueobject.InformacoesAutenticacaoUsuarioRVO;
import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import br.app.harppia.modulo.usuario.application.port.out.ConsultarIgrejaUserToChurchPort;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsultarUsuarioFromAuthAdapter implements ConsultarUsuarioAuthToUsuarioPort {

	private final ConsultarUsuarioUseCase conUsrUC;
	private final ConsultarIgrejaUserToChurchPort conIgrAuthPort;

	@Override
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO informacoesAutenticacao(String cpf, String email, String telefone) {

		InformacoesLoginUsuarioBanco user = conUsrUC.informacoesAutenticacaoLogin(cpf, email, telefone);

		List<RolesMembroPorIgrejaMinisterioRVO> rolUsrPorIgrRVO = conIgrAuthPort.rolesMembro(user.id());
		
		return new InformacoesAutenticacaoUsuarioRVO(user.id(), user.email(), user.senha(), null, rolUsrPorIgrRVO);
	}

	@Override
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO porId(UUID id) {

		InformacoesLoginUsuarioBanco user = conUsrUC.porId(id);
		
		List<RolesMembroPorIgrejaMinisterioRVO> rolUsrPorIgrRVO = conIgrAuthPort.rolesMembro(user.id());

		return (user == null) ? null
				: new InformacoesAutenticacaoUsuarioRVO(user.id(), user.email(), user.senha(), null, rolUsrPorIgrRVO);
	}

	@Override
	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public InformacoesAutenticacaoUsuarioRVO porEmail(String email) {

		InformacoesLoginUsuarioBanco infLgnUsrBnc = conUsrUC.porEmail(email);

		List<RolesMembroPorIgrejaMinisterioRVO> rolUsrPorIgrRVO = conIgrAuthPort.rolesMembro(infLgnUsrBnc.id());
		
		return (infLgnUsrBnc != null)
				? new InformacoesAutenticacaoUsuarioRVO(
						infLgnUsrBnc.id(), infLgnUsrBnc.email(), 
						infLgnUsrBnc.senha(), null,
						rolUsrPorIgrRVO)
				: null;
	}

	@Override
	public UUID idPorEmail(String email) {
		return conUsrUC.idPorEmail(email);
	}
}




