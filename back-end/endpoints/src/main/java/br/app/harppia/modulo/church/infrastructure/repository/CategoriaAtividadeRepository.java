package br.app.harppia.modulo.church.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.app.harppia.modulo.church.infrastructure.repository.entities.CategoriaAtividadeEntity;
import br.app.harppia.modulo.church.infrastructure.repository.enums.ETipoAtividade;
import br.app.harppia.modulo.church.infrastructure.repository.projection.BuscarIdCategoriaIVO;

@Repository
public interface CategoriaAtividadeRepository extends JpaRepository<CategoriaAtividadeEntity, Integer> {

	@Transactional
	public Optional<BuscarIdCategoriaIVO> findIdCategoriaByTipo(ETipoAtividade tipo);
}
