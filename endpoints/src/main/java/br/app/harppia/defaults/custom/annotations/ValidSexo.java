package br.app.harppia.defaults.custom.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.app.harppia.defaults.custom.validators.SexoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Esta interface replica a lógica de validação de gênero do banco de dados.
 */
@Documented
@Constraint(validatedBy = SexoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSexo {
	String message() default "Sexo deve ser 'O', 'M' ou 'F'!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}
