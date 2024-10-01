package org.example.c9laboration2.entities;

import org.example.c9laboration2.service.Warehouse;

import java.time.LocalDate;

public class
PopulateWarehouse {

  public static void populateWarehouse(Warehouse warehouse) {
    // Electronics
    warehouse.addProduct(new Product("1", "Laptop", Category.ELECTRONICS, 10));
    warehouse.addProduct(new Product("2", "Phone", Category.ELECTRONICS, 9));
    warehouse.addProduct(new Product("3", "Smartwatch", Category.ELECTRONICS, 7));
    warehouse.addProduct(new Product("4", "Tablet", Category.ELECTRONICS, 8));
    warehouse.addProduct(new Product("5", "Headphones", Category.ELECTRONICS, 10));

    // Furniture
    warehouse.addProduct(new Product("6", "Chair", Category.FURNITURE, 7));
    warehouse.addProduct(new Product("7", "Sofa", Category.FURNITURE, 8));
    warehouse.addProduct(new Product("8", "Dining Table", Category.FURNITURE, 9));
    warehouse.addProduct(new Product("9", "Wardrobe", Category.FURNITURE, 8));
    warehouse.addProduct(new Product("10", "Bed Frame", Category.FURNITURE, 7));

    // Clothing
    warehouse.addProduct(new Product("11", "T-Shirt", Category.CLOTHING, 6));
    warehouse.addProduct(new Product("12", "Jeans", Category.CLOTHING, 8));
    warehouse.addProduct(new Product("13", "Jacket", Category.CLOTHING, 9));
    warehouse.addProduct(new Product("14", "Sneakers", Category.CLOTHING, 7));
    warehouse.addProduct(new Product("15", "Hat", Category.CLOTHING, 5));

    // Food
    warehouse.addProduct(new Product("16", "Apple", Category.FOOD, 8));
    warehouse.addProduct(new Product("17", "Banana", Category.FOOD, 7));
    warehouse.addProduct(new Product("18", "Bread", Category.FOOD, 9));
    warehouse.addProduct(new Product("19", "Milk", Category.FOOD, 7));
    warehouse.addProduct(new Product("20", "Cheese", Category.FOOD, 10));

    // Toys
    warehouse.addProduct(new Product("21", "Toy Car", Category.TOYS, 6));
    warehouse.addProduct(new Product("22", "Doll", Category.TOYS, 7));
    warehouse.addProduct(new Product("23", "Board Game", Category.TOYS, 10));
    warehouse.addProduct(new Product("24", "Puzzle", Category.TOYS, 9));
    warehouse.addProduct(new Product("25", "Lego Set", Category.TOYS, 10));
  }
}

