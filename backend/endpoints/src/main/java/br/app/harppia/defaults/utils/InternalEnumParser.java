package br.app.harppia.defaults.utils;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public class InternalEnumParser {
    
	/**
	 * Responsável por, dado uma string, retornar a constante definida na enum equivalente.
	 * Exemplo:
	 * -> Para a string "banana" e a enum "Frutas", o método deve retornar "Frutas.BANANA".
	 * @param <T> o tipo da enum
	 * @param enumClass a referência à classe da enum (T.class)
	 * @param value o valor a ser procurado
	 * @return uma instância da enum requisitada
	 */
    public static <T extends Enum<T> & EnumPersistivel> T fromValue(Class<T> enumClass, String value) {
        for (T enumVal : enumClass.getEnumConstants()) {
            if (enumVal.getCustomValue().equalsIgnoreCase(value)) {
                return enumVal;
            }
        }
        throw new IllegalArgumentException("Valor inválido para enum " 
                + enumClass.getSimpleName() + ": " + value);
    }
}
