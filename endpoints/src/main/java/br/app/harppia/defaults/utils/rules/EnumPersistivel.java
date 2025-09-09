/*
 * Autor: Lucas Mateus Braga de Souza
 * Data: 17/06/2025
 * Nome do arquivo: 'EnumPersistivel.java'
 * Descrição: essa enum cria uma regra para que todas elas (que representem enums do banco de dados)
 * 				implementem um método para retornar o valor customizado, visto que o hibernate por si
 * 				só não é capaz de pegar esse valor.
 */

package br.app.harppia.defaults.utils.rules;

public interface EnumPersistivel {
	public String getValorCustomizado();
}