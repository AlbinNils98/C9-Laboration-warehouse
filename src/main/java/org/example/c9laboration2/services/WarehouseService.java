package org.example.c9laboration2.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;
import org.example.c9laboration2.entities.ProductRecord;
import org.example.c9laboration2.entities.Warehouse;

import java.util.List;
import java.util.Optional;

import static org.example.c9laboration2.entities.PopulateWarehouse.populateWarehouse;

@ApplicationScoped
public class WarehouseService {

  private final Warehouse warehouse = new Warehouse();

  public WarehouseService() {
  }

  public void addProduct(Product product) {
    warehouse.addProduct(product);
  }

  public void populateProducts() {
    populateWarehouse(warehouse);
  }

  public List<ProductRecord> getPaginatedProductList(Long productId, Long pageSize) {
    List<ProductRecord> productList = warehouse.getProductList();
    if (productList.isEmpty()) {
      throw new NotFoundException("No products in warehouse");
    }

    if (!(productId > productList.size())) {
      int index = productList.stream()
          .filter(p -> p.id().equals(String.valueOf(productId)))
          .mapToInt(productList::indexOf)
          .sum();
      int size = pageSize.intValue();

      try {
        if (size > productList.size()) {
          size = productList.size() - index;
        }
        return productList.subList(index, (index + size));
      } catch (IndexOutOfBoundsException e) {
        return productList;
      }
    }
    return productList;
  }

  public Optional<ProductRecord> getProductById(String id) throws NotFoundException {
    Optional<ProductRecord> product = warehouse.getProductsById(id);
    if (product.isEmpty()) {
      throw new NotFoundException("No products found with id: " + id);
    }
    return product;
  }

  public List<ProductRecord> getProductsByCategory(Category category) throws NotFoundException {
    List<ProductRecord> productList = warehouse.getProductsByCategory(category);
    if (productList.isEmpty()) {
      throw new NotFoundException("No products found for category: " + category);
    }
    return warehouse.getProductsByCategory(category);
  }

  public void modifyProduct(String id, String typeOfChange, String change) {
    warehouse.modifyProduct(id, typeOfChange, change);
  }

  public int getCount() {
    return warehouse.getProductList().size();
  }
}
