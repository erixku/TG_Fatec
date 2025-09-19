package br.app.harppia.modulo.usuario.login.dto;

/**
 * Esse record é responsável por receber e transportar as informações enviadas
 * pelo cliente para o servidor, contendo apenas o essencial para a
 * funcionalidade de LOGIN.
 */
public record LoginUsuarioDTO(String telefone, String cpf, String email, String senha) {
}
