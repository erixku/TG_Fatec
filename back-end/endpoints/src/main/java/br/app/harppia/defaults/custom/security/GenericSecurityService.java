package br.app.harppia.defaults.custom.security;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.defaults.custom.roles.EChurchAndMinistryRoles;
import br.app.harppia.modulo.auth.application.services.JwtService;
import br.app.harppia.modulo.church.domain.valueobject.RolesMembroPorIgrejaMinisterioRVO;
import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;
import jakarta.servlet.http.HttpServletRequest;

@Service("harppiaSecurityService")
public class GenericSecurityService {
	
	protected JwtService jwtService;
	protected HttpServletRequest request;
	 private static final Logger log = LoggerFactory.getLogger(GenericSecurityService.class);

	public GenericSecurityService(JwtService jwtService, HttpServletRequest request) {
		this.jwtService = jwtService;
		this.request = request;
	}

	public boolean hasSystemRole(String roleEsperada) {
		
		List<String> systemContext = getSystemTokenContext();
		
		return systemContext.stream().anyMatch( c -> c.equalsIgnoreCase(roleEsperada) );
	}
	

	public boolean hasChurchRole(UUID idIgreja, String roleEsperadaString) {
	    if (idIgreja == null || roleEsperadaString == null) return false;

	    List<RolesMembroPorIgrejaMinisterioRVO> contexto = getChurchTokenContext();

	    var dadosDaIgreja = contexto.stream()
	            .filter(c -> c.idIgreja().equals(idIgreja))
	            .findFirst()
	            .orElseThrow(() -> new GestaoIgrejaException(
	                "Você não possui permissão ou vínculo com a igreja de ID: " + idIgreja
	            ));

	    EChurchAndMinistryRoles roleEsperada = EChurchAndMinistryRoles.fromValue(roleEsperadaString);
	    
	    boolean temPermissao = dadosDaIgreja.cargosNaIgreja().stream()
	            .anyMatch(cargoString -> {
	                try {
	                    var roleUsuario = EChurchAndMinistryRoles.fromValue(cargoString);
	                    return roleUsuario.temPermissaoMinima(roleEsperada);
	                } catch (Exception e) {
	                    return false;
	                }
	            });

	    if (!temPermissao) {
	        throw new GestaoIgrejaException(
	            "Seu cargo nesta igreja não permite realizar esta operação. Nível necessário: " + roleEsperada
	        );
	    }

	    return true;
	}
	
	
	public boolean hasMinistryRole(UUID idMinisterio, String roleEsperadaString) {
	    if (idMinisterio == null || roleEsperadaString == null) return false;

	    List<RolesMembroPorIgrejaMinisterioRVO> contexto = getChurchTokenContext();

	    var dadosDoMinisterio = contexto.stream()
	            .flatMap(igreja -> igreja.funcaoPorMinisterio().stream())
	            .filter(m -> m.idMinisterio().equals(idMinisterio))
	            .findFirst()
	            .orElseThrow(() -> new GestaoIgrejaException(
	                "Você não possui vínculo ou cargo neste ministério (ID: " + idMinisterio + ")"
	            ));

	    try {
	    	EFuncaoMembro roleEsperada = EFuncaoMembro.fromValue(roleEsperadaString);
	        
	        EFuncaoMembro roleDoUsuario = dadosDoMinisterio.funcao(); 

	        if (!roleDoUsuario.temPermissaoMinima(roleEsperada)) {
	            throw new GestaoIgrejaException(
	                "Seu cargo (" + roleDoUsuario + ") neste ministério não permite realizar esta operação. Nível necessário: " + roleEsperada
	            );
	        }

	    } catch (IllegalArgumentException e) {
	        log.error("Erro de segurança: Role inexistente solicitada: {}", roleEsperadaString);
	        return false;
	    }

	    return true;
	}

	protected List<String> getSystemTokenContext() {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			return List.of();

		String token = authHeader.substring(7);
		return jwtService.extractSystemRoles(token);
	}
	
	protected List<RolesMembroPorIgrejaMinisterioRVO> getChurchTokenContext() {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			return List.of();

		String token = authHeader.substring(7);
		return jwtService.extractChurchRoles(token);
	}
}
