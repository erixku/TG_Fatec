package br.app.harppia.modulo.usuario.infrasctructure.validator;

import br.app.harppia.modulo.usuario.infrasctructure.annotation.ValidSexo;
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
