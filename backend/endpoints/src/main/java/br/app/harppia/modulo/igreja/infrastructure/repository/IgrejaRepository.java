package br.app.harppia.modulo.igreja.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

public interface IgrejaRepository  extends JpaRepository<IgrejaEntity, UUID>{

}
