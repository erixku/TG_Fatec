package br.app.harppia.modulo.auth.infrastructure.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthToUsuarioPort;
import br.app.harppia.modulo.auth.application.services.JwtService;
import br.app.harppia.modulo.auth.domain.valueobject.InformacoesAutenticacaoUsuarioRVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final ConsultarUsuarioAuthToUsuarioPort conUsrAuthToUsrPort;
	private final JwtService jwtSvc;
	
	public JwtAuthFilter(ConsultarUsuarioAuthToUsuarioPort conUsrAuthToUsrPort, JwtService jwtSvc) {
		this.conUsrAuthToUsrPort = conUsrAuthToUsrPort;
		this.jwtSvc = jwtSvc;
	}
	
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String jwt = authHeader.substring(7);
        final String username; // <-- email do usuário

        try {
            username = jwtSvc.extractUsername(jwt);
        } catch (Exception e) {
        	filterChain.doFilter(request, response);
            return;
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
        	
        	InformacoesAutenticacaoUsuarioRVO userDetails = conUsrAuthToUsrPort.informacoesAutenticacao(username, username, username);
	
	        if (jwtSvc.isTokenValid(jwt, userDetails)) {
	            var authToken = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, userDetails.getAuthorities());
	
	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
        }
        
        filterChain.doFilter(request, response);
    }
}
