package br.app.harppia.modulo.file.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfo;
import br.app.harppia.modulo.file.infrastructure.repository.entities.BucketEntity;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import br.app.harppia.modulo.usuario.infrasctructure.repository.BucketRepository;

@Service
public class BuscarBucketUseCase {

	private final BucketRepository bucketRepository;

	public BuscarBucketUseCase(BucketRepository bucketRepository) {
		this.bucketRepository = bucketRepository;
	}

	public BucketRestricoesUploadInfo findByNome(ENomeBucket nome) {
		Optional<BucketEntity> bucket = bucketRepository.findByNome(nome.getCustomValue());
		
		if (bucket.isEmpty())
			return null;

		return new BucketRestricoesUploadInfo(
				bucket.get().getId(), 
				bucket.get().getNome(),
				bucket.get().getIsDeleted(),
				bucket.get().getTamanhoMinimo(),
				bucket.get().getTamanhoMaximo(),
				bucket.get().getTempoLimiteUpload()
			);
	}
}
