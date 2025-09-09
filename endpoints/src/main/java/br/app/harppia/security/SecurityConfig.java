package br.app.harppia.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.app.harppia.usuario.shared.entity.Usuario;
import br.app.harppia.usuario.shared.repository.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            	.anyRequest().anonymous()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        // --- PARÂMETROS DE COMPROMISSO - APENAS PARA DESENVOLVIMENTO ---
        int saltLength = 16;      // Padrão seguro, não reduzir.
        int hashLength = 32;      // Padrão seguro, não reduzir.
        int parallelism = 1;      // Obrigatório devido à restrição de CPU.
        int memory = 32768;       // 32MB. Mínimo aceitável para o propósito do Argon2.
        int iterations = 3;       // Aumenta ligeiramente o custo de CPU.

        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
    
    @Bean
    protected UserDetailsService userDetailsService(UsuarioRepository userRepository) {
        return username -> {
            List<Usuario> usuario = userRepository.findByEmail(username);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }
            return (UserDetails) usuario;
        };
    }
}
