package br.app.harppia.modulo.music.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.music.infrastructure.repository.entities.MusicaEntity;

@Repository
public interface MusicaRepository extends JpaRepository<MusicaEntity, Long> {

}
