package br.app.harppia.usuario.autenticacao.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.harppia.controllers.auth.usuario.UsuarioService;
import br.app.harppia.controllers.auth.usuario.dto.AuthenticationRequest;
import br.app.harppia.controllers.auth.usuario.dto.AuthenticationResponse;
import br.app.harppia.controllers.auth.usuario.dto.RegisterRequest;
import br.app.harppia.model.auth.dto.UsuarioCadastroDTO;
import br.app.harppia.model.auth.entities.Usuario;
import br.app.harppia.security.JwtService;
import br.app.harppia.usuario.cadastro.repositorys.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioService usuarioService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Orquestra o registro. Delega a criação do usuário e depois gera o token.
     */
    public AuthenticationResponse register(UsuarioCadastroDTO request) {
        Usuario novoUsuario = usuarioService.cadastrarUsuario(request);
        String jwtToken = jwtService.generateToken(novoUsuario);
        return new AuthenticationResponse(jwtToken);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Usuario user = (Usuario) authentication.getPrincipal();

        String jwtToken = jwtService.generateToken(user);
        
        return new AuthenticationResponse(jwtToken);
    }
}