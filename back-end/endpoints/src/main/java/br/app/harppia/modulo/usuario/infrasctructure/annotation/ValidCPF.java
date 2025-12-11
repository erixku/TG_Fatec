package br.app.harppia.modulo.usuario.infrasctructure.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.app.harppia.modulo.usuario.infrasctructure.validator.CpfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 *  Esta interface replica a lógica de validação de CPF do banco de dados.
 */
@Documented
@Constraint(validatedBy = CpfValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCPF {
	String message() default "CPF inválido!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
