package br.app.harppia.configs;

import java.util.Arrays;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.app.harppia.modulo.auth.application.port.out.ConsultarUsuarioAuthPort;
import br.app.harppia.modulo.auth.domain.valueobjects.InformacoesAutenticacaoUsuarioRVO;
import br.app.harppia.modulo.auth.infrastructure.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class AutenticationSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public AutenticationSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:3000", "https://harppia-endpoints.onrender.com")
        );
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            .authorizeHttpRequests(auth -> auth
        		
            	// - - - - - - - - - - //
        		// ENDPOINTS ANÔNIMOS  //
        		// - - - - - - - - - - //
                .requestMatchers(
                    "/v1/users/auth/login"
                ).permitAll()

                // - - - - - - - -  //
        		// DEMAIS ENDPOINTS //
        		// - - - - - - -  - //
                .anyRequest().authenticated()
            )

            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
    
    /**
     * Retorna um `UserDetailsService` com as informações básicas de identificação do usuário
     * @param userRepository
     * @return
     */
    @Bean
    protected UserDetailsService userDetailsService(ConsultarUsuarioAuthPort conUsrPort) {
        return username -> {
        	InformacoesAutenticacaoUsuarioRVO usuario = conUsrPort.informacoesAutenticacao(username, username, username);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }
            return (UserDetails) usuario;
        };
    }
}