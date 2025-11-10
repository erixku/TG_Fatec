package br.app.harppia.modulo.usuario.application.usecases;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.roles.DatabaseRoles;
import br.app.harppia.modulo.usuario.domain.dto.AtualizarUsuarioDTO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import jakarta.transaction.Transactional;

@Service
public class AtualizarUsuarioUseCase {

	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passEncoder;

	public AtualizarUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passEncoder = passEncoder;
	}

	@UseRole(role = DatabaseRoles.ROLE_USUARIO)
	@Transactional
	public boolean execute(AtualizarUsuarioDTO newUserData) {

		Optional<UsuarioEntity> optionalUserData = usuarioRepository.findById(newUserData.idUsuario());

		if (optionalUserData.isEmpty())
			return false;

		UsuarioEntity entity = optionalUserData.get();

		boolean foiAtualizado = atualizarEntidade(newUserData, entity);

		return foiAtualizado;
	}

	private boolean atualizarEntidade(AtualizarUsuarioDTO newData, UsuarioEntity entity) {

		boolean foiModificado = false;

		String antigoNomeCompleto = String.join(" ", entity.getNome(), entity.getSobrenome());
		if (newData.nomeCompleto() != null && !newData.nomeCompleto().equals(antigoNomeCompleto)) {
			String[] nome = newData.nomeCompleto().split(" ", 2);

			entity.setNome(nome[0]);
			entity.setSobrenome(nome.length > 1 ? nome[1] : null);
			foiModificado = true;
		}

		String antigoNomeSocial = String.join(" ", entity.getNomeSocial(), entity.getSobrenomeSocial());
		if (newData.nomeSocialCompleto() != null && !newData.nomeSocialCompleto().equals(antigoNomeSocial)) {
			String[] nomeSocial = newData.nomeSocialCompleto().split(" ", 2); // Limite 2

			entity.setNomeSocial(nomeSocial[0]);
			entity.setSobrenomeSocial(nomeSocial.length > 1 ? nomeSocial[1] : null);
			foiModificado = true;
		}

		if (newData.dataNascimento() != null && !newData.dataNascimento().equals(entity.getDataNascimento())) {
			entity.setDataNascimento(newData.dataNascimento());
			foiModificado = true;
		}

		if (newData.telefone() != null && !newData.telefone().equals(entity.getTelefone())) {
			entity.setTelefone(newData.telefone());
			foiModificado = true;
		}

		if (newData.senha() != null && !newData.senha().isEmpty()) {
			if (!passEncoder.matches(newData.senha(), entity.getSenha())) {
				entity.setSenha(passEncoder.encode(newData.senha()));
				foiModificado = true;
			}
		}

		if (newData.idFotoPerfil() != null && !newData.idFotoPerfil().equals(entity.getIdFotoPerfil())) {
			entity.setIdFotoPerfil(newData.idFotoPerfil());
			foiModificado = true;
		}

		if (foiModificado)
			entity.setUpdatedAt(OffsetDateTime.now());

		return foiModificado;
	}
}
