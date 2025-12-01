package br.app.harppia.modulo.file.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.file.domain.valueobjects.BucketRestricoesUploadInfoCVO;
import br.app.harppia.modulo.file.infrastructure.repository.BucketRepository;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

@Service
public class BuscarBucketService {

	private final BucketRepository bucRep;

	public BuscarBucketService(BucketRepository bucRep) {
		this.bucRep = bucRep;
	}
	
	
	public BucketRestricoesUploadInfoCVO findByNome(ENomeBucket nome) {
		Optional<BucketRestricoesUploadInfoCVO> bucket = bucRep.findByNome(nome.getCustomValue());
		
		return (bucket.isEmpty()) ? null : bucket.get();
	}
}
