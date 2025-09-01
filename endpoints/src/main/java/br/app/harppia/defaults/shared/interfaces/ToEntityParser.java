package br.app.harppia.defaults.shared.interfaces;

/** 
 * Essa interface deve ser implementada em todos os DTOs do projeto. 
 * Seu propósito é garantir que, para todos os DTOs, exista um método 
 * responsável por convertê-los para uma instância da entidade do banco de dados.
 */
public interface ToEntityParser {
	public Object toEntity();
}
