package br.app.harppia.defaults.custom.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.app.harppia.defaults.custom.validators.UfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Essa interface replica a lógica de validação do banco de dados para o campo UF.
 */

@Documented
@Constraint(validatedBy = UfValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUF {
	String message() default "UF inválida!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}
