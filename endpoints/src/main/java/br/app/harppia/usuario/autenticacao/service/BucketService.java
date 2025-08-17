package br.app.harppia.usuario.autenticacao.service;

import java.util.Optional;

import br.app.harppia.model.enums.NomeBucket;
import br.app.harppia.model.storage.entities.Bucket;
import br.app.harppia.usuario.cadastro.repositorys.BucketRepository;

public class BucketService {
	
	private final BucketRepository bucketRepository;
	
	public BucketService(BucketRepository bucketRepository) {
		this.bucketRepository = bucketRepository;
	}
	
	public Optional<Bucket> findByNome(NomeBucket nome) {
		if(nome == null) return null;
		
		Optional<Bucket> bucket = bucketRepository.findByNome(nome.getValorCustomizado());
		
		return (bucket.isPresent()) ? bucket : null;
	}
}
