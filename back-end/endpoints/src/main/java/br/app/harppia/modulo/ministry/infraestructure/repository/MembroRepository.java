package br.app.harppia.modulo.ministry.infraestructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.ministry.infraestructure.repository.entities.UsuarioFuncaoEntity;

@Repository
public interface MembroRepository extends JpaRepository<UsuarioFuncaoEntity, Integer> {

	UsuarioFuncaoEntity findRoleMembroByIdLevita(UUID idLevita);

	@Modifying
	@Transactional
	@Query(value = "UPDATE church.tb_usuario_funcao "
			+ "SET is_deleted = true, deleted_by = :deletedBy, deleted_at = current_timestamp "
			+ "WHERE id = :id", 
			nativeQuery = true)
	int markDeletedById(@Param("id") Integer id, @Param("deletedBy") UUID deletedBy);
}
