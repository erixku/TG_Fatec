package br.app.harppia.modulo.auth.infrastructure.adapter;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.auth.application.services.VerificationCodeService;
import br.app.harppia.modulo.usuario.application.port.out.GerarSalvarCodigoUsuarioToAuthPort;

@Component
public class GerarSalvarCodigoAutenticacaoUsuarioAdapter implements GerarSalvarCodigoUsuarioToAuthPort {

	private final VerificationCodeService vrfCodeSvc; 
	
	public GerarSalvarCodigoAutenticacaoUsuarioAdapter(VerificationCodeService vrfCodeSvc) {
		this.vrfCodeSvc = vrfCodeSvc;
	}

	@Override
	public String proceder(String strEmail) {
		
		if(strEmail == null || strEmail.trim().isEmpty())
			return null;
		
		return vrfCodeSvc.gerarESalvarCodigo(strEmail);
	}

}
