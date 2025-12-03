package br.app.harppia.defaults.custom.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.app.harppia.defaults.custom.roles.EDatabaseRoles;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseRole {
	
	EDatabaseRoles role();
}
