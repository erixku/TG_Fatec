package br.app.harppia.modulo.usuario.infrasctructure.repository;

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

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
	
	@Modifying
	@Transactional
	@Query("update tb_usuario u set u.idFotoPerfil = :idFotoPerfil where u.id = :id")
	int updateFotoById(@Param("idFotoPerfil") UUID idFotoPerfil, @Param("id") UUID id);

	// Geral
//	public Optional<InformacoesLoginUsuario> findByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
	
	// Usado para autenticação
	public Optional<BuscarInformacoesAutenticacaoIVO> findAuthInfoById(UUID id);
	public Optional<BuscarInformacoesAutenticacaoIVO> findAuthInfoByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
	
	// Usado para pesquisa de perfil de usuário
    public Optional<BuscarInformacoesPublicasIVO> findPublicInfoByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
    
    // Usado para validar se um usuário já existe
    public Optional<IdUsuarioCVO> findIdUsuarioByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
}