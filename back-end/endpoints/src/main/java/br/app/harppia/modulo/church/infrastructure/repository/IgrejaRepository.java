package br.app.harppia.modulo.church.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.church.domain.valueobject.InformacaoIgrejaRVO;
import br.app.harppia.modulo.church.infrastructure.repository.entities.IgrejaEntity;
import br.app.harppia.modulo.church.infrastructure.repository.projection.AllRolesMembroIVO;

@Repository
public interface IgrejaRepository extends JpaRepository<IgrejaEntity, UUID> {
	
	public List<InformacaoIgrejaRVO> findIgrejasByNomeContainingIgnoreCase(String strNome);

	@Transactional
	@Modifying
	@Query("SELECT id FROM tb_igreja WHERE id = :id")
	public List<InformacaoIgrejaRVO> findIgrejasByAssociationWithUser(@Param("id") UUID idUsuario);

	@Transactional(readOnly = true)
	@Query(
	    value = """
				SELECT i.id
				FROM church.tb_igreja AS i
				LEFT JOIN church.tb_ministerio_louvor AS m
					ON m.igr_id = i.id
				LEFT JOIN church.tb_administrador AS a
					ON a.igr_id = i.id
				LEFT JOIN church.tb_usuario_funcao AS f
					ON f.min_lou_id = m.id
				WHERE i.s_auth_t_tb_usuario_c_adm_proprietario = :id
				   OR f.s_auth_t_tb_usuario_c_lev = :id
	        """,
	    nativeQuery = true
	)
	public List<UUID> findIdIgrejasByAssociationWithUser(@Param("id") UUID idUsuario);


	@Transactional(readOnly = true)
	@Query(
		value = "SELECT * FROM utils.s_church_f_get_igrejas_ministerios_roles_para_usuario (:id)",
		nativeQuery = true
	)
	public List<AllRolesMembroIVO> findRolesMembroById(@Param("id") UUID idMembro);
}
