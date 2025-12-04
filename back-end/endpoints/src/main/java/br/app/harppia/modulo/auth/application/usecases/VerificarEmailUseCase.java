package br.app.harppia.modulo.auth.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.auth.application.services.VerificationCodeService;
import br.app.harppia.modulo.auth.domain.request.VerificarEmailRequest;
import br.app.harppia.modulo.auth.domain.response.VerificarEmailResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificarEmailUseCase {

	private final VerificationCodeService vrfCodeSvc;

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public VerificarEmailResponse proceder(VerificarEmailRequest vrfEmlReq) {
		
		boolean blnResult = vrfCodeSvc.validarCodigo(vrfEmlReq.email(), vrfEmlReq.codigo()); 
		
		return new VerificarEmailResponse(blnResult);
	}
}
