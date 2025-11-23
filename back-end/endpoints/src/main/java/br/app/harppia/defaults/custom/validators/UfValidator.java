package br.app.harppia.defaults.custom.validators;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Set;

import br.app.harppia.defaults.custom.annotations.ValidUF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Essa classe é responsável pela lógica de validação da UF. Deve ser usada em
 * campos ou parâmetros por meio da anotação personalizada '@ValidUF'.
 */

public class UfValidator implements ConstraintValidator<ValidUF, String> {

	private static final Set<String> UFs_VALIDAS = Set.of("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA",
			"MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");

	private static final Set<String> ESTADOS_VALIDOS_NORMALIZADOS = Set.of("acre", "alagoas", "amapa", "amazonas",
			"bahia", "ceara", "distrito federal", "espirito santo", "goias", "maranhao", "minas gerais",
			"mato grosso do sul", "mato grosso", "para", "paraiba", "pernambuco", "piaui", "parana", "rio de janeiro",
			"rio grande do norte", "rondonia", "roraima", "rio grande do sul", "santa catarina", "sergipe", "sao paulo",
			"tocantins");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty() || value.trim().length() < 2)
			return false;

		value = Normalizer.normalize(value.trim().toLowerCase(), Form.NFC);

		return UFs_VALIDAS.contains(value.toUpperCase()) || ESTADOS_VALIDOS_NORMALIZADOS.contains(value);
	}

}
