package br.app.harppia.usuario.autenticacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.app.harppia.security.JwtService;
import br.app.harppia.usuario.autenticacao.dtos.AutenticacaoUsuarioDTO;
import br.app.harppia.usuario.autenticacao.repositorys.UsuarioRepository;
import br.app.harppia.usuario.autenticacao.requests.AuthenticationRecord;
import br.app.harppia.usuario.autenticacao.requests.AuthenticationResponse;

@Service
public class AutenticacaoUsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository userRepository;
	
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AutenticacaoUsuarioService(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticate(AuthenticationRecord request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        
        AutenticacaoUsuarioDTO authUserDTO = (AutenticacaoUsuarioDTO) authentication.getPrincipal();

        String jwtToken = jwtService.generateToken(authUserDTO);
        
        return new AuthenticationResponse(jwtToken);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail();
	}
}