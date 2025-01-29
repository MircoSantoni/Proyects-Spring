package com.mirco.curso.springboot.app.springboot_crud.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByUsernameValidation.class)
public @interface ExistsByUsername {
    String message() default "Ya existe en la BDD";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
