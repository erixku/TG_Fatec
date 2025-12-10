/*
 * Autor: Lucas Mateus Braga de Souza
 * Data: 17/06/2025
 * Nome do arquivo: 'EnumPersistivel.java'
 * Descrição: essa enum cria uma regra para que todas as enums (que representem enums do banco de dados)
 * 				implementem um método para retornar o valor customizado, visto que o hibernate por si
 * 				só não é capaz de pegar esse valor.
 */

package br.app.harppia.defaults.custom.enums.rules;

/**
 * Todas as enums que implementares essa interface devem, por espelharem informações
 * do banco de dados, implementar o `@JsonValue` e `@JsonCreator` para a serialização
 * e desserialização de objetos (JSON).
 */
public interface EnumPersistivel {
	
	/**
	 * Retorna o nome personalizado de uma constante definida na enum.
	 * Deve ser anotado com o `@JsonValue`, ou equivalente, para serializar
	 * objetos.
	 * @return a string personalizada
	 */
	public String getCustomValue();
}