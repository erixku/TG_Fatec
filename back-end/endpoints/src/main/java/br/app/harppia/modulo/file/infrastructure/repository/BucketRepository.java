package br.app.harppia.modulo.file.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfoCVO;
import br.app.harppia.modulo.file.infrastructure.repository.entities.BucketEntity;

@Repository
public interface BucketRepository extends JpaRepository<BucketEntity, Integer>{
	
	/**
	 * Busca as informações do bucket pela String de seu nome.<br>
	 * Elas estão definidas em: ENomeBucket.java
	 * @param nome nome do bucket
	 * @return um objeto representando todas as informações do bucket
	 */
	@Query(
        value = "SELECT id, is_deleted, nome, tempo_expiracao_upload_em_segundos, tamanho_maximo, tamanho_minimo FROM storage.tb_bucket WHERE nome = CAST(:nome AS utils.s_storage_t_tb_bucket_e_nome)",
        nativeQuery = true
    )
    Optional<BucketRestricoesUploadInfoCVO> findByNome(@Param("nome") String nome);
}

