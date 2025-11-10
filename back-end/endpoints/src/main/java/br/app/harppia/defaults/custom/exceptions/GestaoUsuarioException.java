package br.app.harppia.defaults.custom.exceptions;

public class GestaoUsuarioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GestaoUsuarioException() {
        super("Erro na gestão de usuário.");
    }

    public GestaoUsuarioException(String message) {
        super(message);
    }

    public GestaoUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
