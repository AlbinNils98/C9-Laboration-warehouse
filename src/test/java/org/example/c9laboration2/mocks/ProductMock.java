
package org.example.c9laboration2.mocks;

import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;

import java.time.LocalDate;

public class ProductMock extends Product {

  private final LocalDate creationDate = LocalDate.of(2024, 10, 9);
  private  LocalDate lastModified = LocalDate.of(2024, 10, 9);

  public ProductMock(String name, Category category, int rating) {
    super(name, category, rating);
  }

  @Override
  public LocalDate getCreationDate() {
    return this.creationDate;  // Return the overridden creation date
  }

  @Override
  public LocalDate getLastModified() {
    return this.lastModified;
  }

  @Override
  public void setLastModified(LocalDate date) {
    this.lastModified = date;
  }
}
