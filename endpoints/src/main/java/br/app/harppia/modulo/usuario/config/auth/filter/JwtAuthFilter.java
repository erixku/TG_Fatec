package br.app.harppia.modulo.usuario.config.auth.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.app.harppia.modulo.usuario.autenticacao.dto.UsuarioAutenticacaoTokenDTO;
import br.app.harppia.modulo.usuario.config.auth.service.UsuarioAuthConfigService;
import br.app.harppia.modulo.usuario.config.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final UsuarioAuthConfigService userService;
	private final JwtService jwtService;
	
	public JwtAuthFilter(UsuarioAuthConfigService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}
	
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Caso não ache um cabeçalho ou token válido, passa pro próximo filtro...
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extrai o token, removendo o prefixo "Bearer "
        final String jwt = authHeader.substring(7);
        final String username; // <-- email do usuário

        try {
        	// Tenta extrair o login do usuário
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Se não encontrar o nome, passa pro próximo filtro...
        	filterChain.doFilter(request, response);
            return;
        }
        
        // Se não houver um usuário E ele ainda não estiver autenticado...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
        	
	        // Busca as credenciais e permissões do usuário.
	        UsuarioAutenticacaoTokenDTO userDetails = this.userService.buscarPorLogin(username);
	
	        // Caso ainda válido, adiciona as informações e permissões dele sobre a aplicação
	        if (jwtService.isTokenValid(jwt, userDetails)) {
	            var authToken = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, userDetails.getAuthorities());
	
	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
        }
        
        
        filterChain.doFilter(request, response);
    }
}
