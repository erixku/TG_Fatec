package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.request.InformacoesLoginUsuario;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;

@Component
public class ConsultarUsuarioAdapter implements ConsultarUsuarioPort {
	private ConsultarUsuarioUseCase consultarUsuarioService;

	public ConsultarUsuarioAdapter(ConsultarUsuarioUseCase consultarUsuarioService) {
		this.consultarUsuarioService = consultarUsuarioService;
	}

	@Override
	public InformacoesLoginUsuario findByCpfOrEmailOrTelefone(String cpf, String email, String Telefone) {

		UsuarioEntity user = consultarUsuarioService.buscarUsuarioPorCpfOuEmailOuTelefone(cpf, email, Telefone);

		// = = = = = = = = = = = = = = = = = = = = = =//
		// IMPLEMENTAR A BUSCA PELOS ROLES DO USUÁRIO //
		// = = = = = = = = = = = = = = = = = = = = = =//
		// ...
		
		
		return new InformacoesLoginUsuario(user.getUuid(), user.getNome(), user.getEmail(), user.getSenha(),
				List.of(new SimpleGrantedAuthority("LEVITA")));
	}

}
