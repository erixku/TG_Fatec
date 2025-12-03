package br.app.harppia.modulo.usuario.application.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.ESystemRoles;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.projection.RolesUsuarioIgrejaMinisterioProjection;

@Service("userSecurity")
public class UsuarioSecurityService {
	
	private final UsuarioRepository usrRep;
	
	public UsuarioSecurityService(UsuarioRepository usrRep) {
		this.usrRep = usrRep;
	}

	@Cacheable(value = "churchPermissions", key = "#churchId + ':' + #authentication.name")
    public boolean check(Authentication authentication, UUID churchId, String roleName) {
        
        if (authentication == null || !authentication.isAuthenticated()) 
            return false;

        String emailUsuario = authentication.getName();

        ESystemRoles eSysRolPerfilNecessario;
        try {
            eSysRolPerfilNecessario = ESystemRoles.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GestaoUsuarioException("Role configurada inválida: " + roleName);
        }

        Optional<UUID> optIdUser = usrRep.findIdByEmail(emailUsuario);
        
        if(optIdUser.isEmpty())
        	return false;
        
        List<RolesUsuarioIgrejaMinisterioProjection> lstRoleUsrIgrMinPjt = usrRep.findRolesUsuarioById(optIdUser.get());

        ESystemRoles eSysRolUser = null;
        for(RolesUsuarioIgrejaMinisterioProjection row : lstRoleUsrIgrMinPjt) {
        	if(row.getIgreja() == churchId)
        		eSysRolUser = ESystemRoles.fromValue(row.getFuncao());
        }

        return (eSysRolUser != null) ? eSysRolPerfilNecessario.temPermissaoMinima(eSysRolUser) : null;
    }
	
}















