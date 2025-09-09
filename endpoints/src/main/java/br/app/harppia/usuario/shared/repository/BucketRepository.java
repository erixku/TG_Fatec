package br.app.harppia.usuario.shared.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.app.harppia.usuario.shared.entity.Bucket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Integer>{
	
	@Query(
        value = "SELECT * FROM storage.tb_bucket WHERE nome = CAST(:nome AS app_utils.enum_s_storage_t_tb_bucket_c_nome)",
        nativeQuery = true
    )
    Optional<Bucket> findByNome(@Param("nome") String nome);
}

