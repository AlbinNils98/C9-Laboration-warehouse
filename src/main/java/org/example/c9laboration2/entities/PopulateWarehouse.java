package org.example.c9laboration2.entities;

import org.example.c9laboration2.service.Warehouse;

import java.time.LocalDate;

public class PopulateWarehouse {

  public static void populateWarehouse(Warehouse warehouse) {
    // Electronics
    warehouse.addProduct(new Product("1", "Laptop", Category.ELECTRONICS, 10, LocalDate.of(2024, 9, 6), LocalDate.of(2023, 1, 13)));
    warehouse.addProduct(new Product("2", "Phone", Category.ELECTRONICS, 9, LocalDate.of(2023, 3, 20), LocalDate.of(2023, 3, 20)));
    warehouse.addProduct(new Product("3", "Smartwatch", Category.ELECTRONICS, 7, LocalDate.of(2023, 4, 5), LocalDate.of(2023, 4, 5)));
    warehouse.addProduct(new Product("4", "Tablet", Category.ELECTRONICS, 8, LocalDate.of(2023, 5, 12), LocalDate.of(2023, 5, 12)));
    warehouse.addProduct(new Product("5", "Headphones", Category.ELECTRONICS, 10, LocalDate.of(2024, 9, 18), LocalDate.of(2023, 6, 18)));

    // Furniture
    warehouse.addProduct(new Product("6", "Chair", Category.FURNITURE, 7, LocalDate.of(2023, 2, 15), LocalDate.of(2023, 2, 15)));
    warehouse.addProduct(new Product("7", "Sofa", Category.FURNITURE, 8, LocalDate.of(2023, 7, 22), LocalDate.of(2023, 7, 22)));
    warehouse.addProduct(new Product("8", "Dining Table", Category.FURNITURE, 9, LocalDate.of(2023, 8, 30), LocalDate.of(2023, 8, 30)));
    warehouse.addProduct(new Product("9", "Wardrobe", Category.FURNITURE, 8, LocalDate.of(2024, 9, 10), LocalDate.of(2023, 9, 10)));
    warehouse.addProduct(new Product("10", "Bed Frame", Category.FURNITURE, 7, LocalDate.of(2023, 10, 5), LocalDate.of(2023, 10, 5)));

    // Clothing
    warehouse.addProduct(new Product("11", "T-Shirt", Category.CLOTHING, 6, LocalDate.of(2023, 1, 25), LocalDate.of(2023, 1, 25)));
    warehouse.addProduct(new Product("12", "Jeans", Category.CLOTHING, 8, LocalDate.of(2023, 3, 10), LocalDate.of(2023, 3, 10)));
    warehouse.addProduct(new Product("13", "Jacket", Category.CLOTHING, 9, LocalDate.of(2023, 11, 15), LocalDate.of(2023, 11, 15)));
    warehouse.addProduct(new Product("14", "Sneakers", Category.CLOTHING, 7, LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 1)));
    warehouse.addProduct(new Product("15", "Hat", Category.CLOTHING, 5, LocalDate.of(2023, 6, 22), LocalDate.of(2023, 6, 22)));

    // Food
    warehouse.addProduct(new Product("16", "Apple", Category.FOOD, 8, LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 3)));
    warehouse.addProduct(new Product("17", "Banana", Category.FOOD, 7, LocalDate.of(2023, 3, 6), LocalDate.of(2023, 3, 6)));
    warehouse.addProduct(new Product("18", "Bread", Category.FOOD, 9, LocalDate.of(2023, 4, 14), LocalDate.of(2023, 4, 14)));
    warehouse.addProduct(new Product("19", "Milk", Category.FOOD, 7, LocalDate.of(2023, 4, 18), LocalDate.of(2023, 4, 18)));
    warehouse.addProduct(new Product("20", "Cheese", Category.FOOD, 10, LocalDate.of(2024, 9, 25), LocalDate.of(2023, 5, 25)));

    // Toys
    warehouse.addProduct(new Product("21", "Toy Car", Category.TOYS, 6, LocalDate.of(2023, 8, 12), LocalDate.of(2023, 8, 12)));
    warehouse.addProduct(new Product("22", "Doll", Category.TOYS, 7, LocalDate.of(2023, 9, 22), LocalDate.of(2023, 9, 22)));
    warehouse.addProduct(new Product("23", "Board Game", Category.TOYS, 10, LocalDate.of(2024, 9, 2), LocalDate.of(2023, 10, 2)));
    warehouse.addProduct(new Product("24", "Puzzle", Category.TOYS, 9, LocalDate.of(2023, 11, 10), LocalDate.of(2023, 11, 10)));
    warehouse.addProduct(new Product("25", "Lego Set", Category.TOYS, 10, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 1)));
  }
}

