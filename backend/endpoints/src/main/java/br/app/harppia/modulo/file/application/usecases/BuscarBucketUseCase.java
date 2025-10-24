package br.app.harppia.modulo.file.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.file.domain.valueobjects.BucketBasicInfo;
import br.app.harppia.modulo.file.infrastructure.repository.entities.BucketEntity;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import br.app.harppia.modulo.usuario.infrasctructure.repository.BucketRepository;

@Service
public class BuscarBucketUseCase {

	private final BucketRepository bucketRepository;

	public BuscarBucketUseCase(BucketRepository bucketRepository) {
		this.bucketRepository = bucketRepository;
	}

	/**
	 * Dado um nome, contido em <b>ENomeBucket</b>, busca pelo bucket. 
	 * @param nome nome do bucket
	 * @return um record com o id e nome do bucket ou null
	 */
	public BucketBasicInfo findByNome(ENomeBucket nome) {
		Optional<BucketEntity> bucket = bucketRepository.findByNome(nome.getCustomValue());
		
		if (bucket.isEmpty())
			return null;

		return new BucketBasicInfo(bucket.get().getId(), bucket.get().getNome());
	}
}
