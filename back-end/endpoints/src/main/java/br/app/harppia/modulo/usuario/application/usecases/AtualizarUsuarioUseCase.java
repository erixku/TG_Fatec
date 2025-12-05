package br.app.harppia.modulo.usuario.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.aop.UseRole;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.roles.EDatabaseRoles;
import br.app.harppia.modulo.usuario.domain.dto.AtualizarUsuarioDTO;
import br.app.harppia.modulo.usuario.infrasctructure.repository.UsuarioRepository;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import br.app.harppia.modulo.usuario.infrasctructure.repository.enums.EStatusUsuario;
import jakarta.transaction.Transactional;

@Service
public class AtualizarUsuarioUseCase {

	private UsuarioRepository usrRep;
	private PasswordEncoder pwdEnc;

	public AtualizarUsuarioUseCase(UsuarioRepository usrRep, PasswordEncoder pwdEnc) {
		this.usrRep = usrRep;
		this.pwdEnc = pwdEnc;
	}

	@UseRole(role = EDatabaseRoles.ROLE_USUARIO)
	@Transactional
	public boolean execute(AtualizarUsuarioDTO newUserData) {

		Optional<UsuarioEntity> optionalUserData = usrRep.findById(newUserData.idUsuario());

		if (optionalUserData.isEmpty())
			return false;

		UsuarioEntity entity = optionalUserData.get();

		boolean foiAtualizado = atualizarEntidade(newUserData, entity);

		return foiAtualizado;
	}

	@Transactional
	@UseRole(role = EDatabaseRoles.ROLE_ANONIMO)
	public int marcarUsuarioComoAtivo(UUID id) {
		
		Optional<UsuarioEntity> optUsrData = usrRep.findById(id);
		
		if(optUsrData.isEmpty())
			throw new GestaoUsuarioException("Erro ao ativar conta de usuário: id inexistente!");
		
		if(optUsrData.get().getStatus() == EStatusUsuario.ATIVO)
			return 1;
		
		optUsrData.get().setStatus(EStatusUsuario.ATIVO);
		
		UsuarioEntity usrEntSaved = usrRep.save(optUsrData.get());
		
		return (usrEntSaved.getStatus() == EStatusUsuario.ATIVO) ? 1 : 0;
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
			if (!pwdEnc.matches(newData.senha(), entity.getSenha())) {
				entity.setSenha(pwdEnc.encode(newData.senha()));
				foiModificado = true;
			}
		}

		if (newData.idFotoPerfil() != null && !newData.idFotoPerfil().equals(entity.getIdFotoPerfil())) {
			entity.setIdFotoPerfil(newData.idFotoPerfil());
			foiModificado = true;
		}

		return foiModificado;
	}
	
	
}
