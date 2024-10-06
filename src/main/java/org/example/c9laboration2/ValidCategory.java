package org.example.c9laboration2;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CategoryValidator.class)
@Documented

public @interface ValidCategory {
  String message() default "Invalid Category";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};


}
