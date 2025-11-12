package br.app.harppia.modulo.userconfig.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.userconfig.infrastructure.repository.entities.AcessibilidadeAuditivaEntity;

@Repository
public interface AcessibilidadeAuditivaRepository extends JpaRepository<AcessibilidadeAuditivaEntity, UUID> {
	
	@Modifying
	@Transactional
	@Query("insert into tb_auditiva (id) values(:slvAcbAudReq)")
	void firstSave(@Param("slvAcbAudReq") UUID slvAcbAudReq);
}
