package br.app.harppia.modulo.userconfig.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeIntelectualEntity;

@Repository
public interface AcessibilidadeIntelectualRepository extends JpaRepository<AcessibilidadeIntelectualEntity, UUID> {
	
	@Modifying
	@Transactional
	@Query("insert into tb_intelectual (id) values(:idDonoConfig)")
	void firstSave(@Param("idDonoConfig") UUID idDonoConfig);
}
