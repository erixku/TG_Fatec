package br.app.harppia.modulo.ministry.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.ministry.infraestructure.repository.entities.UsuarioFuncaoEntity;

@Repository
public interface MembroRepository extends JpaRepository<UsuarioFuncaoEntity, Integer> {
}
