package br.app.harppia.modulo.auth.domain.response;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import br.app.harppia.modulo.auth.domain.valueobject.IgrejasUsuarioFazParteRVO;
import br.app.harppia.modulo.auth.domain.valueobject.InformacaoUsuarioLoginRVO;
import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import lombok.Builder;

@Builder
public record LoginUsuarioResponse(
		InformacaoUsuarioLoginRVO infUsrLogRVO,
		
		IgrejasUsuarioFazParteRVO igrUsrFazPrtRVO,
		
		Collection<? extends GrantedAuthority> systemRoles,
		
		List<RolesMembroPorIgrejaMinisterioRVO> churchRoles,
		
		String accessToken, String refreshToken
	) {

}
