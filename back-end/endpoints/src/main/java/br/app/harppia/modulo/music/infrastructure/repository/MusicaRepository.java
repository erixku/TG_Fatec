package br.app.harppia.modulo.music.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.music.domain.valueobject.InformacoesBasicasMusicaRVO;
import br.app.harppia.modulo.music.infrastructure.repository.entities.MusicaEntity;

@Repository
public interface MusicaRepository extends JpaRepository<MusicaEntity, Long> {

	// Usado para retornar uma lista de musicas
	List<InformacoesBasicasMusicaRVO> findMusicasByNomeContainingIgnoreCase(String nome);

}
