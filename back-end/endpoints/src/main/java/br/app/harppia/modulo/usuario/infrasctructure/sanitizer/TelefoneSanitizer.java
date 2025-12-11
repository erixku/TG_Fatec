package br.app.harppia.modulo.usuario.infrasctructure.sanitizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Essa classe é dedicada para validar telefones para serem 
 * persistidos.
 */
public abstract class TelefoneSanitizer {

    // Lista de DDDs brasileiros válidos
    private static final Set<String> DDD_VALIDOS = new HashSet<>(Arrays.asList(
        "11","12","13","14","15","16","17","18","19",
        "21","22","24",
        "27","28",
        "31","32","33","34","35","37","38",
        "41","42","43","44","45","46",
        "47","48","49",
        "51","53","54","55",
        "61","62","64","65","66","67",
        "68","69",
        "71","73","74","75","77",
        "79","81","82","83","84","85","86","87","88","89",
        "91","92","93","94","95","96","97","98","99"
    ));

    public static String sanitize(String telefone) throws IllegalArgumentException{
        if (telefone == null)
        	return null;

        // Remover caracteres invisíveis (tabs, \n, etc.)
        telefone = telefone.replaceAll("\\p{C}", "");

        // Manter apenas dígitos
        telefone = telefone.replaceAll("[^0-9]", "");

        if (telefone.length() != 11) 
            throw new IllegalArgumentException("Telefone inválido: deve ter exatamente 11 dígitos (DDD + número)");

        // Validar DDD
        String ddd = telefone.substring(0, 2);
        if (!DDD_VALIDOS.contains(ddd)) 
            throw new IllegalArgumentException("Telefone inválido: DDD inválido");

        // Validar primeiro dígito do número
        if (telefone.charAt(2) != '9') 
            throw new IllegalArgumentException("Telefone inválido: o número deve começar com 9");

        return telefone;
    }
}
