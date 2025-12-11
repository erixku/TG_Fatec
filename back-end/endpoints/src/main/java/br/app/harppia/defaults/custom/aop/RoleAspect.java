package br.app.harppia.defaults.custom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Aspect
@Component
public class RoleAspect {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Este 'pointcut' agora intercepta qualquer método anotado com @ComRole e, o
	 * mais importante, ele 'injeta' a própria anotação (comRole) como um parâmetro
	 * para que possamos ler seus atributos.
	 */
	@Around("@annotation(useRole)")
	public Object executarComRole(ProceedingJoinPoint joinPoint, UseRole useRole) throws Throwable {

		String roleName = useRole.role().getValue();

		Object resultado = null;

		try {
			em.createNativeQuery("SET ROLE \"" + roleName + "\"").executeUpdate();

			resultado = joinPoint.proceed();

		} finally {
			em.createNativeQuery("RESET ROLE").executeUpdate();
		}

		return resultado;
	}
}