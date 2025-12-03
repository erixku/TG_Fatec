package br.app.harppia.modulo.ministry.application.service;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthPort;
import br.app.harppia.modulo.ministry.infraestructure.repository.MembroRepository;
import br.app.harppia.modulo.ministry.infraestructure.repository.entities.UsuarioFuncaoEntity;
import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MembroService {

    private final MembroRepository memRep;
    private final ConsultarUsuarioAuthPort conUsrAuthPort;

    @Transactional
    @CacheEvict(value = "churchPermissions", key = "#churchId + ':' + #email")
    public void atualizarCargo(Long churchId, String email, EFuncaoMembro novoPerfil) {
        
    	UUID idUsuario = conUsrAuthPort.porEmail(email);
    	
        UsuarioFuncaoEntity usrFncEntParaAtualizar = memRep.findRoleMembroByIdLevita(idUsuario);
            
        usrFncEntParaAtualizar.setFuncao(novoPerfil);
        memRep.save(usrFncEntParaAtualizar);        
    }


    @Transactional
    @CacheEvict(value = "churchPermissions", key = "#churchId + ':' + #email")
    public int removerMembro(UUID idRemovedor, UUID churchId, String email) {
    	
        UsuarioFuncaoEntity membro = memRep.findRoleMembroByIdLevita(conUsrAuthPort.porEmail(email));
            
        return memRep.markDeletedById(membro.getId(), idRemovedor);
    }
}