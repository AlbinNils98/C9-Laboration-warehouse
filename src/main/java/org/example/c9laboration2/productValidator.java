package org.example.c9laboration2;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class productValidator {
  private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
  private static Validator validator = validatorFactory.getValidator();

  public static <T> Set<ConstraintViolation<T>> validate(T entity){
    return validator.validate(entity);
  }
}
