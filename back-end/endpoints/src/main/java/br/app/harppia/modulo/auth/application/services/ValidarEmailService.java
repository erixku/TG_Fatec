package br.app.harppia.modulo.auth.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoAutenticacaoException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.auth.application.port.out.AtualizarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.auth.application.usecases.LogarUsuarioUseCase;
import br.app.harppia.modulo.auth.domain.request.ConfirmarEmailRequest;
import br.app.harppia.modulo.auth.domain.response.ConfirmarEmailResponse;
import br.app.harppia.modulo.auth.domain.response.LoginUsuarioResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidarEmailService {

	private final ConsultarUsuarioAuthToUsuarioPort conUsrAuthToUsrPort;
	private final AtualizarUsuarioAuthToUsuarioPort atuUsrAuthToUsrPort;
	
	private final VerificationCodeService vrfCodeSvc;
	private final LogarUsuarioUseCase logUsrUC;

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public ConfirmarEmailResponse proceder(ConfirmarEmailRequest cnfEmlReq) {
		
		boolean blnResultadoValidacao = vrfCodeSvc.validarCodigo(cnfEmlReq.email(), cnfEmlReq.codigo());
		
		if(!blnResultadoValidacao)
			throw new GestaoAutenticacaoException("Erro ao verificar email: código inválido!");
		
		UUID idUsuario = conUsrAuthToUsrPort.idPorEmail(cnfEmlReq.email());
		
		int intResultadoAtualizacao = atuUsrAuthToUsrPort.marcarContaComoVerificada(idUsuario);
		
		if(intResultadoAtualizacao == 0)
			throw new GestaoAutenticacaoException("Não foi possível verificar o usuário.");
		
		LoginUsuarioResponse lgnUsrRes = logUsrUC.aposValidarEmail(cnfEmlReq.email(), cnfEmlReq.senha());
		
		return new ConfirmarEmailResponse(lgnUsrRes);
	}
}
