package br.app.harppia.modulo.usuario.infrasctructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesAutenticacaoIVO;
import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesPublicasIVO;
import br.app.harppia.modulo.usuario.domain.valueobject.IdUsuarioCVO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import br.app.harppia.modulo.usuario.infrasctructure.repository.projection.RolesUsuarioIgrejaMinisterioProjection;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
	
	@Modifying
	@Transactional
	@Query("update tb_usuario u set u.idFotoPerfil = :idFotoPerfil where u.id = :id")
	int updateFotoById(@Param("idFotoPerfil") UUID idFotoPerfil, @Param("id") UUID id);
	
	// Usado para autenticação
	public Optional<BuscarInformacoesAutenticacaoIVO> findAuthInfoById(UUID id);
	public Optional<UUID> findIdByEmail(String email);
	public Optional<BuscarInformacoesAutenticacaoIVO> findAuthInfoByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
	
	// usado para buscar os roles do usuário para validar as roles nos endpoints
	@Query(value = "SELECT igreja, ministerio, funcao, role_usuario_igreja " +
            "FROM utils.s_church_f_get_igrejas_ministerios_roles_para_usuario(:usuarioId)",
    nativeQuery = true)
	public List<RolesUsuarioIgrejaMinisterioProjection> findRolesUsuarioById(UUID id);
	
	
	// Usado para pesquisa de perfil de usuário
    public Optional<BuscarInformacoesPublicasIVO> findPublicInfoByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
    
    // Usado para validar se um usuário já existe
    public Optional<IdUsuarioCVO> findIdUsuarioByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
}