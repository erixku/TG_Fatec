package br.app.harppia.modulo.file.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.app.harppia.modulo.file.infrastructure.repository.entities.ArquivoEntity;

public interface ArquivoRepository extends JpaRepository<ArquivoEntity, UUID> {

}
