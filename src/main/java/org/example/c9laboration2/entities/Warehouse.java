package org.example.c9laboration2.entities;

import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.function.Function;

public class Warehouse {
  private final List<Product> productList = new CopyOnWriteArrayList<>();

  public void addProduct(Product product) {
    if (product.getName() == null || product.getName().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be empty");
    }
    String Id = String.valueOf(productList.size() + 1);
    product.setId(Id);
    productList.add(product);
  }

  public void modifyProduct(String id, String typOfChange, String change) {

    List<Product> filteredProducts = productList.stream()
        .filter(p -> p.getId().equals(id))
        .toList();

    if (filteredProducts.isEmpty()) {
      System.out.println("No product found of ID: " + id);
      return;
    }

    Product product = filteredProducts.getFirst();
    switch (typOfChange) {
      case ("category"):
        product.setCategory(Category.valueOf(change.toUpperCase()));
        product.setLastModified(LocalDate.now());
        System.out.println("Product modified successfully.");
        break;

      case ("rating"):
        product.setRating(Integer.parseInt(change));
        product.setLastModified(LocalDate.now());
        System.out.println("Product modified successfully.");
        break;

      case ("name"):
        product.setName(change);
        product.setLastModified(LocalDate.now());
        System.out.println("Product modified successfully.");
        break;

      default:
        System.out.println("Invalid Input");
    }
  }

  public List<ProductRecord> getProductList() {
    if (productList.isEmpty()) {
      System.out.println("Warehouse is empty");
    }

    return productList.stream()
        .map(p -> new ProductRecord(
            p.getId(),
            p.getName(),
            p.getCategory(),
            p.getRating(),
            p.getCreationDate(),
            p.getLastModified()
        )).toList();
  }

  public List<ProductRecord> getProductsByCategory(Category category) {
    return ifNothingFoundInFilteredList(
        getProductList().stream()
            .filter(p -> p.category().equals(category))
            .sorted(Comparator.comparing(ProductRecord::name))
            .toList(), "category", String.valueOf(category));
  }

  public Optional<ProductRecord> getProductsById(String id) {
    return getProductList().stream()
        .filter(p -> p.id().equals(id)).findFirst();
  }

  public List<ProductRecord> getProductsByCreationDate(LocalDate date) {
    return ifNothingFoundInFilteredList(
        getProductList()
            .stream()
            .filter(p -> p.creationDate().isAfter(date))
            .toList()
        , "creationDate", date.toString());
  }

  public List<ProductRecord> getModifiedProducts() {
    return ifNothingFoundInFilteredList(
        getProductList()
            .stream()
            .filter(p -> !p.lastModified().equals(p.creationDate()))
            .toList(), "lastModified", "since creation date"); //fix name
  }

  public List<Category> getCategoriesWithProducts() {
    Set<Category> categories = getProductList().stream()
        .map(ProductRecord::category)
        .collect(Collectors.toSet());

    return ifNothingFoundInFilteredList(new ArrayList<>(categories), "category", "with at least one product");
  }

  public int countProductsInCategory(Category category) {
    return (int) getProductList().stream()
        .filter(p -> p.category().equals(category))
        .count();
  }

  public Map<Character, Long> numberOfProductsWithSameFirstLetter() {
    return getProductList().stream()
        .map(p -> p.name().charAt(0))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  public List<ProductRecord> getProductsWithMaxRatingThisMonth(LocalDate date) {

    int MAXRATING = 10;
    return ifNothingFoundInFilteredList(getProductList().stream()
        .filter(p -> p.rating() == MAXRATING //CHANGE THIS IF RATING SCALE CHANGES
            && !p.creationDate().isBefore(date.withDayOfMonth(1))
            && !p.creationDate().isAfter(date))
        .sorted(Comparator.comparing(ProductRecord::creationDate))
        .toList(), "rating", String.valueOf(MAXRATING));
  }

  private <T> List<T> ifNothingFoundInFilteredList(List<T> list, String identifier, String identifierName) throws NotFoundException {
    if (list.isEmpty()) {
      System.out.println("No product found of " + identifier + ": " + identifierName);
    }
    return list;
  }
}

