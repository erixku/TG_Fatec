/*
 * Autor: Lucas Mateus Braga de Souza
 * Data: 17/06/2025
 * Nome do arquivo: 'EnumPersistivel.java'
 * Descrição: essa enum cria uma regra para que todas as enums (que representem enums do banco de dados)
 * 				implementem um método para retornar o valor customizado, visto que o hibernate por si
 * 				só não é capaz de pegar esse valor.
 */

package br.app.harppia.defaults.utils.rules;

/**
 * Essa interface obriga a implementação de um método para retornar o valor
 * customizado definido numa Enum que representa o outra enum do banco de dados.
 */
public interface EnumPersistivel {
	public String getCustomValue();
}