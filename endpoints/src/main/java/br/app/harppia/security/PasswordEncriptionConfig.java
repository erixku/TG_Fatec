package br.app.harppia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncriptionConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // --- PARÂMETROS DE COMPROMISSO - APENAS PARA DESENVOLVIMENTO ---
        int saltLength = 16;      // Padrão seguro, não reduzir.
        int hashLength = 32;      // Padrão seguro, não reduzir.
        int parallelism = 1;      // Obrigatório devido à restrição de CPU.
        int memory = 32768;       // 32MB. Mínimo aceitável para o propósito do Argon2.
        int iterations = 3;       // Aumenta ligeiramente o custo de CPU.

        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
}