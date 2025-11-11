package br.app.harppia.modulo.file.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfoCVO;
import br.app.harppia.modulo.file.infrastructure.repository.BucketRepository;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

@Service
public class BuscarBucketUseCase {

	private final BucketRepository bucketRepository;

	public BuscarBucketUseCase(BucketRepository bucketRepository) {
		this.bucketRepository = bucketRepository;
	}
	
	
	public BucketRestricoesUploadInfoCVO findByNome(ENomeBucket nome) {
		Optional<BucketRestricoesUploadInfoCVO> bucket = bucketRepository.findByNome(nome.getCustomValue());
		
		return (bucket.isEmpty()) ? null : bucket.get();
	}
}
