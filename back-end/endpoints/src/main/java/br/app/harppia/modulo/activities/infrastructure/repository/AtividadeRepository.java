package br.app.harppia.modulo.activities.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.activities.infrastructure.repository.entities.AtividadeEntity;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity, Integer> {

}
