package org.example.c9laboration2.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

  private final Validator validator;

  public ProductTest() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      this.validator = factory.getValidator();
    }
  }

  @Test
  @DisplayName("Should not allow a blank product name")
  void shouldNotAllowBlankName() {
    Product product = new Product("", Category.ELECTRONICS, 5);

    Set<ConstraintViolation<Product>> violations = validator.validate(product);
    Set<String> messages = violations.stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toSet());

    assertThat(violations).isNotEmpty();
    assertThat(messages).contains("Product name cannot be blank");
  }

  @Test
  @DisplayName("Should not allow a null category")
  void shouldNotAllowNullCategory() {
    Product product = new Product("Laptop", null, 5);

    Set<ConstraintViolation<Product>> violations = validator.validate(product);
    Set<String> messages = violations.stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toSet());

    assertThat(violations).isNotEmpty();
    assertThat(messages).contains("Category cannot be null");
  }

  @Test
  @DisplayName("Should not allow a rating of less then 1")
  void shouldNotAllowRatingLessThan1() {
    Product product = new Product("Laptop", Category.ELECTRONICS, 0);

    Set<ConstraintViolation<Product>> violations = validator.validate(product);
    Set<String> messages = violations.stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toSet());

    assertThat(violations).isNotEmpty();
    assertThat(messages).contains("Rating must be at least 1");
  }

  @Test
  @DisplayName("Should not allow a rating greater than 10")
  void shouldNotAllowRatingGreaterThan10() {
    Product product = new Product("Laptop", Category.ELECTRONICS, 11);

    Set<ConstraintViolation<Product>> violations = validator.validate(product);
    Set<String> messages = violations.stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toSet());

    assertThat(violations).isNotEmpty();
    assertThat(messages).contains("Rating cannot be more then 10");
  }

  @Test
  @DisplayName("Should allow a valid product")
  void shouldAllowValidProduct() {
    Product product = new Product("Laptop", Category.ELECTRONICS, 5);

    Set<ConstraintViolation<Product>> violations = validator.validate(product);

    assertThat(violations).isEmpty();
  }
}
