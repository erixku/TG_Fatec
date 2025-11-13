package br.app.harppia.modulo.igreja.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.app.harppia.modulo.igreja.infrastructure.repository.entities.EnderecoIgrejaEntity;

public interface EnderecoIgrejaRepository extends JpaRepository<EnderecoIgrejaEntity, Integer> {

}
