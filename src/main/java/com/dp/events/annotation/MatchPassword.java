package com.dp.events.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.dp.events.validators.MatchPasswordValidator;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { MatchPasswordValidator.class})
public @interface MatchPassword {
	String message();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}