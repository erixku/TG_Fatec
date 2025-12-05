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
	        SELECT DISTINCT igr.id
	        FROM church.tb_igreja AS igr
	        LEFT JOIN church.tb_ministerio_louvor AS min_lou
	            ON min_lou.igr_id = igr.id
	        LEFT JOIN church.tb_administrador AS admins
	            ON admins.igr_id = igr.id
	        LEFT JOIN church.tb_usuario_funcao AS usr_func
	            ON usr_func.min_lou_id = min_lou.id
	        WHERE admins.s_auth_t_tb_usuario_c_adm = :id
	           OR usr_func.s_auth_t_tb_usuario_c_lev = :id
	        """,
	    nativeQuery = true
	)
	public List<UUID> findIdIgrejasByAssociationWithUser(@Param("id") UUID idUsuario);


}
