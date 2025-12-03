package br.app.harppia.configs;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.app.harppia.defaults.custom.exceptions.CPFValidationException;
import br.app.harppia.defaults.custom.exceptions.GestaoArquivoException;
import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.defaults.custom.exceptions.GestaoUsuarioException;
import br.app.harppia.defaults.custom.exceptions.JwtServiceExcpetion;

@RestControllerAdvice
public class WarningController {

    private static final Logger log = LoggerFactory.getLogger(WarningController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Erro interno não tratado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                    "Erro interno no servidor",
                    "Ocorreu um erro inesperado. Nossa equipe já foi notificada.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    OffsetDateTime.now().toString()
                ));
    }

    @ExceptionHandler(CPFValidationException.class)
    public ResponseEntity<ErrorResponse> handleCPFError(CPFValidationException ex) {
        log.warn("Erro ao validar CPF: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                    "Erro de validação de CPF",
                    ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    OffsetDateTime.now().toString()
                ));
    }

    @ExceptionHandler(GestaoUsuarioException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioError(GestaoUsuarioException ex) {
        log.warn("Erro de gestão de usuário: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                    "Erro ao processar dados de usuário",
                    ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    OffsetDateTime.now().toString()
                ));
    }

    @ExceptionHandler(GestaoArquivoException.class)
    public ResponseEntity<ErrorResponse> handleArquivoError(GestaoArquivoException ex) {
        log.error("Erro ao salvar arquivo", ex);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ErrorResponse(
                    "Erro ao processar arquivo",
                    ex.getMessage(),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                    OffsetDateTime.now().toString()
                ));
    }

    @ExceptionHandler(JwtServiceExcpetion.class)
    public ResponseEntity<ErrorResponse> handleJwtError(JwtServiceExcpetion ex) {
        log.warn("Erro de autenticação JWT: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                    "Erro de autenticação",
                    ex.getMessage(),
                    HttpStatus.UNAUTHORIZED.value(),
                    OffsetDateTime.now().toString()
                ));
    }

    @ExceptionHandler(GestaoIgrejaException.class)
    public ResponseEntity<ErrorResponse> handleIgrejaError(GestaoIgrejaException ex) {
        log.error("Erro ao processar transação de igreja", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                    "Erro ao completar transação",
                    ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    OffsetDateTime.now().toString()
                ));
    }

    public record ErrorResponse(String mensagem, String detalhe, int status, String timestamp) {}
}
