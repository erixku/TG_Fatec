package br.app.harppia.modulo.igreja.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.igreja.infrastructure.repository.entities.AdministradorIgrejaEntity;

@Repository
public interface AdministradorIgrejaRepository extends JpaRepository<AdministradorIgrejaEntity, Long> {

}
