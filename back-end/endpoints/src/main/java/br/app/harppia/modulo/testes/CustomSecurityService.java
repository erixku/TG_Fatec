package br.app.harppia.modulo.testes;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.harppia.modulo.auth.application.services.JwtService;
import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import jakarta.servlet.http.HttpServletRequest;

@Service("testSecurityService")
public class CustomSecurityService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private HttpServletRequest request;

	public boolean hasChurchRole(UUID idIgreja, String roleEsperada) {

		System.err.println("VERIFICANDO SEGURANÇA: Igreja=" + idIgreja + ", Role=" + roleEsperada);
		
		List<RolesMembroPorIgrejaMinisterioRVO> contexto = obterContextoDoToken();

		return contexto.stream().anyMatch(c -> c.idIgreja().equals(idIgreja)
				&& c.cargosNaIgreja().stream().anyMatch(d -> d.equalsIgnoreCase(roleEsperada)));
	}

	public boolean hasMinistryRole(UUID idMinisterio, String funcaoEsperada) {
		List<RolesMembroPorIgrejaMinisterioRVO> contexto = obterContextoDoToken();

		return contexto.stream().flatMap(c -> c.funcaoPorMinisterio().stream())
				.anyMatch(m -> m.idMinisterio().equals(idMinisterio)
						&& m.funcao().getCustomValue().equalsIgnoreCase(funcaoEsperada));
	}

	private List<RolesMembroPorIgrejaMinisterioRVO> obterContextoDoToken() {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			return List.of();

		String token = authHeader.substring(7);
		return jwtService.extractRolesIgreja(token);
	}
}