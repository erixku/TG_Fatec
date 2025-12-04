package br.app.harppia.modulo.auth.application.services;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private final StringRedisTemplate redisTemplate;
    
    private static final String KEY_PREFIX = "confirmacao:";
    private static final long EXPIRATION_MINUTES = 15;

    public String gerarESalvarCodigo(String email) {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        
        String chave = KEY_PREFIX + email;
        
        redisTemplate.opsForValue().set(chave, codigo, EXPIRATION_MINUTES, TimeUnit.MINUTES);
        
        return codigo;
    }

    public boolean validarCodigo(String email, String codigoEnviado) {
        String chave = KEY_PREFIX + email;
        String codigoSalvo = redisTemplate.opsForValue().get(chave);

        if (codigoSalvo == null) {
            throw new BadCredentialsException("Código expirado ou inválido.");
        }

        if (!codigoSalvo.equals(codigoEnviado)) {
            throw new BadCredentialsException("Código incorreto.");
        }

        return redisTemplate.delete(chave);
    }
}