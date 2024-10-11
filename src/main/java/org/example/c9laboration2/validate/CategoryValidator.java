package org.example.c9laboration2.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.c9laboration2.entities.Category;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

  @Override
  public void initialize(ValidCategory constraintAnnotation) {
  }

  @Override
  public boolean isValid(String category, ConstraintValidatorContext context) {
    if (category == null || category.trim().isEmpty()) {
      return false;
    }

    try {
      Category.valueOf(category.toUpperCase());
      return true;
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }
}