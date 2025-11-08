package br.app.harppia.modulo.igreja.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

@Repository
public interface IgrejaRepository  extends JpaRepository<IgrejaEntity, UUID>{

}
