package br.app.harppia.modulo.usuario.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.modulo.usuario.domain.dto.InformacaoPublicaUsuarioDTO;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;

@Service
public class ConsultarUsuarioUseCase {

	private UsuarioRepository userRepo;

	public ConsultarUsuarioUseCase(UsuarioRepository userRepo) {
		this.userRepo = userRepo;
	}

	public InformacaoPublicaUsuarioDTO buscarUsuarioPorCpfOuEmailOuTelefone(String cpf, String email, String telefone) {
		Optional<UsuarioEntity> user = userRepo.findByCpfOrEmailOrTelefone(cpf, email, telefone);

		return (user.isEmpty()) ? null
				: new InformacaoPublicaUsuarioDTO(user.get().getId(), user.get().getNome(), user.get().getNomeSocial(),
						user.get().getEmail(), user.get().getCpf(), user.get().getIdFotoPerfil());
	}

	public InformacoesLoginUsuarioBanco buscarInformacoesLogin(String cpf, String email, String telefone) {
		Optional<UsuarioEntity> user = userRepo.findByCpfOrEmailOrTelefone(cpf, email, telefone);

		return (user.isEmpty()) ? null
				: new InformacoesLoginUsuarioBanco(user.get().getId(), user.get().getNome(), user.get().getCpf(),
						user.get().getEmail(), user.get().getTelefone(), user.get().getSenha());
	}
}
