package br.app.harppia.modulo.usuario.cadastro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.shared.enums.NomeBucket;
import br.app.harppia.modulo.usuario.shared.entity.Bucket;
import br.app.harppia.modulo.usuario.shared.repository.BucketRepository;

@Service
public class BucketCadastroService {

	
	
	// AJEITAR AS VALIDAÇÕES DE ENTRADA DE DADOS
	
	
	
	
	@Autowired
	private BucketRepository bucketRepository;

	public Optional<Bucket> findByNome(NomeBucket nome) {
		Optional<Bucket> bucket = bucketRepository.findByNome(nome.getCustomValue());

		if(bucket.isPresent())
			return bucket;
		
		return Optional.ofNullable(bucket.get());
	}
}
