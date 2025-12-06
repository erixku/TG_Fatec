package br.app.harppia.modulo.auth.domain.response;

import br.app.harppia.modulo.auth.domain.valueobject.IgrejasUsuarioFazParteRVO;
import br.app.harppia.modulo.auth.domain.valueobject.InformacaoUsuarioLoginRVO;
import lombok.Builder;

@Builder
public record LoginUsuarioResponse(
		InformacaoUsuarioLoginRVO infUsrLogRVO,
		
		IgrejasUsuarioFazParteRVO igrUsrFazPrtRVO,
		
		String accessToken, String refreshToken
	) {

}
