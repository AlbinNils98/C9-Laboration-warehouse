package org.example.c9laboration2.service;

import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.ProductRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.example.c9laboration2.entities.Product;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class WarehouseTest {
  private Warehouse warehouse;
  private Warehouse warehouseWithNoMaxRating;
  private Warehouse noneModiefiedWarehouse;

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  private Warehouse emptyWarehouse;

  @BeforeEach
  void setUp() {
    // Mock the products
    outputStream.reset();

    Product mockProduct1 = new Product("1", "Product 1", Category.ELECTRONICS, 5, LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 4));
    Product mockProduct2 = new Product("2", "Product 2", Category.FURNITURE, 10, LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 3));
    Product mockProduct3 = new Product("3", "Product 3", Category.FURNITURE, 10, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 10));
    Product mockProduct4 = new Product("4", "Product 4", Category.FURNITURE, 10, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 1));

    // Create the mocked warehouse
    warehouse = Mockito.spy(new Warehouse());

    // Inject the products into the warehouse's productList
    warehouse.addProduct(mockProduct1);
    warehouse.addProduct(mockProduct2);
    warehouse.addProduct(mockProduct3);
    warehouse.addProduct(mockProduct4);

    emptyWarehouse = Mockito.spy(new Warehouse());

    noneModiefiedWarehouse = Mockito.spy(new Warehouse());
    noneModiefiedWarehouse.addProduct(mockProduct2);


    warehouseWithNoMaxRating = Mockito.spy(new Warehouse());
    warehouseWithNoMaxRating.addProduct(mockProduct1);

    System.setOut(new PrintStream(outputStream));
  }

  static class CommonAssertions {

    public void assertProduct1(ProductRecord product) {
      assertAll("Attributes",
              () -> assertEquals("1", product.id()),
              () -> assertEquals("Product 1", product.name()),
              () -> assertEquals(Category.ELECTRONICS, product.category()),
              () -> assertEquals(5, product.rating()),
              () -> assertEquals(LocalDate.of(2024, 3, 3), product.creationDate()),
              () -> assertEquals(LocalDate.of(2024, 3, 4), product.lastModified())
      );
    }

    public void assertProduct2(ProductRecord product) {
      assertAll("Attributes",
              () -> assertEquals("2", product.id()),
              () -> assertEquals("Product 2", product.name()),
              () -> assertEquals(Category.FURNITURE, product.category()),
              () -> assertEquals(10, product.rating()),
              () -> assertEquals(LocalDate.of(2024, 3, 3), product.creationDate()),
              () -> assertEquals(LocalDate.of(2024, 3, 3), product.lastModified())
      );
    }

    public void assertOutputMessage(ByteArrayOutputStream outputStream, String expected) {
      String message = outputStream.toString().trim();
      assertThat(message).contains(expected);
    }
  }

  @Nested
  class GetProductList extends CommonAssertions {
    @Test
    @DisplayName("Should return message if empty list")
    void shouldReturnMessageIfEmptyList() {
      List<ProductRecord> filteredList = emptyWarehouse.getProductList();
      assertAll(
              () -> assertThat(filteredList).isEmpty(),
              () -> assertOutputMessage(outputStream, "Warehouse is empty")
      );
    }

    @Test
    @DisplayName("Should return a product list")
    void shouldReturnAProductList() {
      assertThat(warehouse.getProductList()).isNotEmpty();
    }
  }

  @Nested
  class GetById extends CommonAssertions {
    @Test
    @DisplayName("Should print message if wrong product id")
    void shouldPrintIfWrongProductId() {
      List<ProductRecord> filteredList = warehouse.getProductsById("1337");
      assertAll(
              () -> assertTrue(filteredList.isEmpty()),
              () -> assertOutputMessage(outputStream, "No product found of ID: 1337")
      );

    }

    @Test
    @DisplayName("Should return product with correct attributes")
    void shouldReturnProductWithCorrectAttributes() {
      List<ProductRecord> filteredList = warehouse.getProductsById("1");
      assertProduct1(filteredList.getFirst());
    }
  }

  @Nested
  class GetByCategory extends CommonAssertions {
    @Test
    @DisplayName("should print message if not existing category in warehouse")
    void shouldPrintMessageIfNotExistingCategoryInWarehouse() {
      assertAll("Attributes",
              () -> {
                List<ProductRecord> filteredList = warehouse.getProductsByCategory(Category.HEALTH);
                String message = outputStream.toString().trim();
                assertTrue(filteredList.isEmpty());
                assertThat(message).contains("No product found of category: HEALTH");
              });
    }


    @Test
    @DisplayName("should return not empty list if product exists")
    void shouldReturnNotEmptyListIfProductExists() {
      assertAll("Attributes",
              () -> {
                List<ProductRecord> filteredList = warehouse.getProductsByCategory(Category.ELECTRONICS);
                assertProduct1(filteredList.getFirst());
              });
    }
  }

  @Nested
  class GetProductsWithMaxRatingThisMonth extends CommonAssertions {
    @Test
    @DisplayName("Should return message in console when nothing found")
    void shouldReturnMessageInConsoleWhenNothingFound() {
      warehouseWithNoMaxRating.getProductsWithMaxRatingThisMonth(LocalDate.of(2024, 2, 14));
      assertOutputMessage(outputStream, "No product found of rating: 10");
    }

    @Test
    @DisplayName("Should return only ten in rating")
    void shouldReturnOnlyTenInRating() {
      List<ProductRecord> productList = warehouse.getProductsWithMaxRatingThisMonth(LocalDate.of(2024, 3, 9));
      assertProduct2(productList.getFirst());
    }

    @Test
    @DisplayName("Should return products in todays month")
    void shouldReturnProductsInTodaysMonth() {
      LocalDate todaysMonthFirstDay = LocalDate.of(2024, 3, 1);
      LocalDate today = LocalDate.of(2024, 3, 14);
      List<ProductRecord> productList = warehouse.getProductsWithMaxRatingThisMonth(today);
      assertTrue(productList.getFirst().creationDate().isBefore(today) &&
              productList.getFirst().creationDate().isAfter(todaysMonthFirstDay));
    }
  }

  @Nested
  class CountProductsInCategory {
    @Test
    @DisplayName("Should return correct number for each Category")
    void shouldReturnCorrectNumberForEachCategory() {
      int total = warehouse.countProductsInCategory(Category.FURNITURE);
      assertEquals(3, total);
    }
  }

  @Nested
  class GetCategoriesWithProducts extends CommonAssertions {
    @Test
    @DisplayName("Should print message if no products found")
    void shouldPrintMessageIfNoProductsFound() {
      emptyWarehouse.getCategoriesWithProducts();
      assertOutputMessage(outputStream, "No product found of category: with at least one product");
    }

    @Test
    @DisplayName("Should return categories if they have at least one item")
    void shouldReturnCategoriesIfTheyHaveAtLeastOneItem() {
      assertThat(warehouse.getCategoriesWithProducts()).isNotEmpty();
    }
  }

  @Nested
  class AddProduct {

    @Test
    @DisplayName("Should add a valid product successfully")
    void shouldAddValidProductSuccessfully() {
      Product newProduct = new Product("5", "New Product", Category.HEALTH, 8, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 1));
      warehouse.addProduct(newProduct);
      List<ProductRecord> productList = warehouse.getProductList();
      assertThat(productList).extracting(ProductRecord::id).contains("5");
      assertThat(productList).extracting(ProductRecord::name).contains("New Product");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding product with empty name")
    void shouldThrowIllegalArgumentExceptionWhenAddingProductWithEmptyName() {
      Product invalidProduct = new Product("6", "", Category.HEALTH, 8, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 1));
      Exception exception = assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(invalidProduct));
      assertThat(exception.getMessage()).isEqualTo("Product name cannot be empty");
    }
  }

  @Nested
  class ModifyProduct extends CommonAssertions {

    @ParameterizedTest
    @CsvSource({
            "1, category, FURNITURE",
            "1, rating, 9",
            "1, name, Product X"
    })
    @DisplayName("Should modify product successfully")
    void shouldModifyProductSuccessfully(String id, String typeOfChange, String change) {
      warehouse.modifyProduct(id, typeOfChange, change);
      List<ProductRecord> productList = warehouse.getProductsById(id);
      ProductRecord modifiedProduct = productList.stream().findFirst().orElse(null);
      assertNotNull(modifiedProduct);
      switch (typeOfChange) {
        case "category":
          assertEquals(Category.valueOf(change.toUpperCase()), modifiedProduct.category());
          break;
        case "rating":
          assertEquals(Integer.parseInt(change), modifiedProduct.rating());
          break;
        case "name":
          assertEquals(change, modifiedProduct.name());
          break;
        default:
          fail("Unsupported field type");
      }
      assertAll(
              () -> assertEquals(LocalDate.now(), modifiedProduct.lastModified()),
              () -> assertOutputMessage(outputStream, "Product modified successfully")
      );
    }

    @Test
    @DisplayName("Should print message when product ID does not exist")
    void shouldPrintMessageWhenProductIDDoesNotExist() {
      warehouse.modifyProduct("999", "name", "Product 25");
      assertOutputMessage(outputStream, "No product found of ID: 999");
    }

    @Test
    @DisplayName("Should print message when typeOfChange is invalid")
    void shouldPrintMessageWhenTypeOfChangeIsInvalid() {
      warehouse.modifyProduct("1", "year", "2025");
      assertOutputMessage(outputStream, "Invalid Input");
    }
  }

  @Nested
  class GetProductsByCreationDate extends CommonAssertions {
    @Test
    @DisplayName("Should return products created after given date")
    void shouldReturnProductsCreatedAfterGivenDate() {
      LocalDate date = LocalDate.of(2024, 1, 1);
      List<ProductRecord> productList = warehouse.getProductsByCreationDate(date);
      assertThat(productList).isNotEmpty();
      assertThat(productList).allMatch(p -> p.creationDate().isAfter(date));
    }

    @Test
    @DisplayName("Should return empty list and print message when no products found after date")
    void shouldReturnEmptyListAndPrintMessageWhenNoProductsFoundAfterDate() {
      LocalDate date = LocalDate.of(2025, 1, 1);
      List<ProductRecord> productList = warehouse.getProductsByCreationDate(date);
      assertThat(productList).isEmpty();
      assertOutputMessage(outputStream, "No product found of creationDate: 2025-01-01");
    }
  }

  @Nested
  class GetModifiedProducts extends CommonAssertions {
    @Test
    @DisplayName("Should print message if no modified products are found")
    void shouldPrintMessageIfNoModifiedProducts() {
      noneModiefiedWarehouse.getModifiedProducts();
      String message = outputStream.toString().trim();
      assertEquals("No product found of lastModified: since creation date", message);
    }

    @Test
    @DisplayName("Should return products with different lastModified and creationDate")
    void shouldReturnProductsWithDifferentLastModified() {
      List<ProductRecord> modifiedProducts = warehouse.getModifiedProducts();
      assertProduct1(modifiedProducts.getFirst());
    }

  }

  @Nested
  class NumberOfProductWithSameFirstLetters {
    @Test
    @DisplayName("Should return map with counted first letters")
    void shouldMapWithCountedFirstLetters() {
      Map<Character, Long> productLetterCount = warehouse.numberOfProductsWithSameFirstLetter();
      Map<Character, Long> expected = new HashMap<>() {
        {
          put('P', 4L);
        }
      };
      assertEquals(expected, productLetterCount);
    }
  }
}
