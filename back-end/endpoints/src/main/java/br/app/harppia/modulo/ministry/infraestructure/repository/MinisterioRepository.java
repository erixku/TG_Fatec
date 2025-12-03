package br.app.harppia.modulo.ministry.infraestructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.ministry.infraestructure.repository.entities.MinisterioEntity;
import br.app.harppia.modulo.ministry.infraestructure.repository.projection.InformacaoMinisterioProjection;

@Repository
public interface MinisterioRepository extends JpaRepository<MinisterioEntity, UUID> {

	@Transactional(readOnly = true)
	public InformacaoMinisterioProjection findByNomeContainingIgnoreCase( String nome );

	@Transactional(readOnly = true)
	public List<InformacaoMinisterioProjection> findAllByNomeContainingIgnoreCase( String nome );
	
}
