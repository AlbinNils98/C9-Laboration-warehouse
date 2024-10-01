package org.example.c9laboration2.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;
import org.example.c9laboration2.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;

import static org.example.c9laboration2.entities.PopulateWarehouse.populateWarehouse;

@ApplicationScoped
public class WarehouseService {

  private final Warehouse warehouse = new Warehouse();

  public WarehouseService(){
    populateWarehouse(this.warehouse);
  }

  public void addProduct(Product product) {
    warehouse.addProduct(product);
  }

  public List<ProductRecord> getAllProducts() {
    return warehouse.getProductList();
  }

  public List<ProductRecord> getProductsByPage(String productId, String pageSize) {
    List<ProductRecord> productList = warehouse.getProductList();
    int index = productList.indexOf(getProductById(productId));
    int size;

    try {
      size = Integer.parseInt(pageSize);
    } catch (NumberFormatException e) {
      return List.of();
    }
    try {
      if (size > productList.size()) {
          size = productList.size();
      }
      return productList.subList(index, (index + size));
    }catch (IndexOutOfBoundsException e){
      return List.of();
    }
  }

  public ProductRecord getProductById(String id) {
    List<ProductRecord> products = warehouse.getProductsById(id);
    return products.isEmpty() ? null : products.get(0);
  }

  public List<ProductRecord> getProductsByCategory(Category category) {
    return warehouse.getProductsByCategory(category);
  }

  public void modifyProduct(String id, String typeOfChange, String change) {
    warehouse.modifyProduct(id, typeOfChange, change);
  }

  // You can add other methods here, like pagination, filtering, etc.
}
