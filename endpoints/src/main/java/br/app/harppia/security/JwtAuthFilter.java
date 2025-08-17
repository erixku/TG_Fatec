package br.app.harppia.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Obter o cabeçalho 'Authorization' da requisição.
        final String authHeader = request.getHeader("Authorization");

        // 2. Validar o cabeçalho. Se estiver nulo ou não começar com "Bearer ",
        // passamos para o próximo filtro sem fazer nada.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrair o token JWT. (Começa após "Bearer ")
        final String jwt = authHeader.substring(7);
        final String username;
        
        try {
            // 4. Extrair o nome de usuário (username) de dentro do token.
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Se houver qualquer erro na extração (token malformado, expirado, etc.),
            // apenas continuamos a cadeia de filtros. A requisição não será autenticada.
            // Opcionalmente, pode-se logar o erro aqui.
            filterChain.doFilter(request, response);
            return;
        }


        // 5. Se temos um username e o usuário ainda não está autenticado no contexto de segurança...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // a. Carregamos os detalhes do usuário a partir do banco de dados.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // b. Validamos se o token é válido para aquele usuário.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // c. Se o token for válido, criamos um objeto de autenticação.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credenciais são nulas pois já validamos via token.
                        userDetails.getAuthorities()
                );
                
                // d. Adicionamos detalhes da requisição web (ex: IP de origem) ao nosso objeto de autenticação.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // e. Finalmente, atualizamos o SecurityContextHolder. A partir deste ponto, o usuário está autenticado para esta requisição.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 6. Passamos a requisição (seja ela autenticada ou não) para o próximo filtro na cadeia.
        filterChain.doFilter(request, response);
    }
}