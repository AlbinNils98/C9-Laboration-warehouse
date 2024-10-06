package org.example.c9laboration2.entities;

import org.example.c9laboration2.service.Warehouse;

import java.time.LocalDate;

public class
PopulateWarehouse {

  public static void populateWarehouse(Warehouse warehouse) {
    // Electronics
    warehouse.addProduct(new Product("Laptop", Category.ELECTRONICS, 10));
    warehouse.addProduct(new Product("Phone", Category.ELECTRONICS, 9));
    warehouse.addProduct(new Product("Smartwatch", Category.ELECTRONICS, 7));
    warehouse.addProduct(new Product("Tablet", Category.ELECTRONICS, 8));
    warehouse.addProduct(new Product("Headphones", Category.ELECTRONICS, 10));

    // Furniture
    warehouse.addProduct(new Product("Chair", Category.FURNITURE, 7));
    warehouse.addProduct(new Product("Sofa", Category.FURNITURE, 8));
    warehouse.addProduct(new Product("Dining Table", Category.FURNITURE, 9));
    warehouse.addProduct(new Product("Wardrobe", Category.FURNITURE, 8));
    warehouse.addProduct(new Product("Bed Frame", Category.FURNITURE, 7));

    // Clothing
    warehouse.addProduct(new Product("T-Shirt", Category.CLOTHING, 6));
    warehouse.addProduct(new Product("Jeans", Category.CLOTHING, 8));
    warehouse.addProduct(new Product("Jacket", Category.CLOTHING, 9));
    warehouse.addProduct(new Product("Sneakers", Category.CLOTHING, 7));
    warehouse.addProduct(new Product("Hat", Category.CLOTHING, 5));

    // Food
    warehouse.addProduct(new Product("Apple", Category.FOOD, 8));
    warehouse.addProduct(new Product("Banana", Category.FOOD, 7));
    warehouse.addProduct(new Product("Bread", Category.FOOD, 9));
    warehouse.addProduct(new Product("Milk", Category.FOOD, 7));
    warehouse.addProduct(new Product("Cheese", Category.FOOD, 10));

    // Toys
    warehouse.addProduct(new Product("Toy Car", Category.TOYS, 6));
    warehouse.addProduct(new Product("Doll", Category.TOYS, 7));
    warehouse.addProduct(new Product("Board Game", Category.TOYS, 10));
    warehouse.addProduct(new Product("Puzzle", Category.TOYS, 9));
    warehouse.addProduct(new Product("Lego Set", Category.TOYS, 10));
  }
}

