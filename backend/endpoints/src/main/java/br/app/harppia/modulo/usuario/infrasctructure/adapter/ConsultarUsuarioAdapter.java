package br.app.harppia.modulo.usuario.infrasctructure.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioPort;
import br.app.harppia.modulo.auth.domain.auth.request.InformacoesLoginUsuario;
import br.app.harppia.modulo.usuario.application.usecases.ConsultarUsuarioUseCase;
import br.app.harppia.modulo.usuario.domain.dto.login.InformacoesLoginUsuarioBanco;

@Component
public class ConsultarUsuarioAdapter implements ConsultarUsuarioPort {
	private ConsultarUsuarioUseCase consultarUsuarioService;

	public ConsultarUsuarioAdapter(ConsultarUsuarioUseCase consultarUsuarioService) {
		this.consultarUsuarioService = consultarUsuarioService;
	}

	@Override
	public InformacoesLoginUsuario findByCpfOrEmailOrTelefone(String cpf, String email, String Telefone) {

		InformacoesLoginUsuarioBanco user = consultarUsuarioService.buscarInformacoesLogin(cpf, email, Telefone);

		// = = = = = = = = = = = = = = = = = = = = = =//
		// IMPLEMENTAR A BUSCA PELOS ROLES DO USUÁRIO //
		// = = = = = = = = = = = = = = = = = = = = = =//
		// ...
		if(user == null) return null;
		
		return new InformacoesLoginUsuario(user.id(), user.nome(), user.email(), user.senha(),
				List.of(new SimpleGrantedAuthority("LEVITA")));
	}

	@Override
	public InformacoesLoginUsuario findById(UUID id) {
		InformacoesLoginUsuarioBanco user = consultarUsuarioService.buscarPorId(id);

		if(user == null) 
			return null;
		
		return new InformacoesLoginUsuario(user.id(), user.nome(), user.email(), user.senha(),
				List.of(new SimpleGrantedAuthority("LEVITA")));
	}

}
