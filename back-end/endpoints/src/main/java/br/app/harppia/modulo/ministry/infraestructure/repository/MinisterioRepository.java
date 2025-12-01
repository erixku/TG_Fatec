package br.app.harppia.modulo.ministry.infraestructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.ministry.infraestructure.repository.entities.MinisterioEntity;

@Repository
public interface MinisterioRepository extends JpaRepository<MinisterioEntity, UUID> {

}
