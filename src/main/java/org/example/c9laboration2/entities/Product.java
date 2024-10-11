package org.example.c9laboration2.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalDate;

public class Product {

  private String id;

  @NotBlank(message = "Product name cannot be blank")
  private String name;

  @NotNull(message = "Category cannot be null")
  private Category category;

  @Min(value = 1, message = "Rating must be at least 1")
  @Max(value = 10, message = "Rating cannot be more then 10")
  private int rating;

  private final LocalDate creationDate = LocalDate.now();

  private LocalDate lastModified = LocalDate.now();

  public Product(String name, Category category, int rating) {
    setName(name);
    setCategory(category);
    setRating(rating);
  }

  public Product() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public LocalDate getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDate lastModified) {
    this.lastModified = lastModified;
  }
}

