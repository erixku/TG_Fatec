package br.app.harppia.defaults.custom.validators;

import br.app.harppia.defaults.custom.annotations.ValidSexo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * Essa classe deve ser usada na declaração de atributos ou parâmetros de DTOs.
 */
public class SexoValidator implements ConstraintValidator<ValidSexo, Character>{

	@Override
	public boolean isValid(Character value, ConstraintValidatorContext context) {
		if(value == null) return false;
		
		value = Character.toUpperCase(value);
		
		return (value == 'M' || value == 'F' || value == 'O');
	}
	
}
