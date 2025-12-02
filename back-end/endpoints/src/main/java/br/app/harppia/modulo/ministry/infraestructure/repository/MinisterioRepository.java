package br.app.harppia.modulo.ministry.infraestructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.ministry.domain.valueobject.InformacaoMinisterioRVO;
import br.app.harppia.modulo.ministry.infraestructure.repository.entities.MinisterioEntity;

@Repository
public interface MinisterioRepository extends JpaRepository<MinisterioEntity, UUID> {

	@Transactional(readOnly = true)
	public InformacaoMinisterioRVO findByNameContainingIgnoreCase( String nome );

	@Transactional(readOnly = true)
	public List<InformacaoMinisterioRVO> findMinisteriosByNameContainingIgnoreCase( String nome );
	public List<InformacaoMinisterioRVO> findAllIds();
	
}
