package br.app.harppia.defaults.custom.enums.rules;

/**
 * Serve para validar se determinadas Strings estão presentes em uma enum.
 * Replica o comportamento do "List.contains()" em enums.
 */
public interface ContentExistsValidation<E extends Enum<E> & EnumPersistivel & ContentExistsValidation<E> > {

    /**
     * Verifica se o valor existe entre as constantes de uma enum.
     *
     * @param enumClass Classe da enum a verificar.
     * @param value Valor a ser procurado.
     * @return true se o valor existir na enum, false caso contrário.
     */
    static <E extends Enum<E> & EnumPersistivel & ContentExistsValidation<E>> boolean contains(Class<E> enumClass, String value) {
        if (value == null) return false;
        for (E constant : enumClass.getEnumConstants()) {
            String customValue = constant.getCustomValue();
            if (customValue != null && customValue.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
