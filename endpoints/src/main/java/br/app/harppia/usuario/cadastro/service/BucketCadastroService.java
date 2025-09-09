package br.app.harppia.usuario.cadastro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.app.harppia.defaults.shared.enums.NomeBucket;
import br.app.harppia.usuario.shared.entity.Bucket;
import br.app.harppia.usuario.shared.repository.BucketRepository;

public class BucketCadastroService {

	@Autowired
	private BucketRepository bucketRepository;

	public Optional<Bucket> findByNome(NomeBucket nome) {
		if (nome == null)
			return null;

		Optional<Bucket> bucket = bucketRepository.findByNome(nome.getValorCustomizado());

		return (bucket.isPresent()) ? bucket : null;
	}
}
