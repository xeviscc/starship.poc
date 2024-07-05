package com.w2m.starship.poc.domain.starship;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for starship operations.
 */
@Aspect
@Component
public class StarshipAspect {

	private static final Logger logger = LoggerFactory.getLogger(StarshipAspect.class);

	/**
	 * Advice to log when a negative ID is received in the getStarshipById method of StarshipController.
	 *
	 * @param joinPoint the join point at which the advice is applied
	 * @param id        the ID parameter of the method
	 */
	@Before("execution(* com.w2m.starship.poc.application.starship.controllers.StarshipController.getStarshipById(..)) && args(id,..)")
	public void logNegativeId(JoinPoint joinPoint, Long id) {
		if (id < 0) {
			logger.warn("Negative ID received: {}", id);
		}
	}
}