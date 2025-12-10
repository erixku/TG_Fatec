package br.app.harppia.modulo.activities.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.activities.infrastructure.projection.DadosPublicacaoIVO;
import br.app.harppia.modulo.activities.infrastructure.repository.entities.PublicacaoEntity;

@Repository
public interface PublicacaoRepository extends JpaRepository<PublicacaoEntity, Integer> {

	@Transactional(readOnly = true)
	public List<DadosPublicacaoIVO> getAllByIdIgreja(UUID idIgreja);

	@Transactional(readOnly = true)
	public List<DadosPublicacaoIVO> getAllByIdIgrejaAndIdMinisterio(UUID idIgreja, UUID idMinisterio);
}