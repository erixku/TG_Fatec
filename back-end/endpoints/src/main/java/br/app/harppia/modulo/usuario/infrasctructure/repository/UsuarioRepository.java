package br.app.harppia.modulo.usuario.infrasctructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.app.harppia.modulo.usuario.domain.valueobject.BuscarInformacoesPublicasVO;
import br.app.harppia.modulo.usuario.domain.valueobject.IdUsuarioCVO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
	public List<UsuarioEntity> findByNome(String nome);
	public List<UsuarioEntity> findByEmail(String email);
	public List<UsuarioEntity> findByCpf(String cpf);
	public List<UsuarioEntity> findByTelefone(String telefone);
	public Optional<UsuarioEntity> findByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
    public Optional<BuscarInformacoesPublicasVO> findPublicInfoByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
    public Optional<IdUsuarioCVO> findIdUsuarioByCpfOrEmailOrTelefone(String cpf, String email, String telefone);
}