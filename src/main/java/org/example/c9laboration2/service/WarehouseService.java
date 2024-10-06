package org.example.c9laboration2.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;
import org.example.c9laboration2.entities.ProductRecord;

import java.util.InputMismatchException;
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

  public List<ProductRecord> getPaginatedProductList(Long productId, Long pageSize) {
    List<ProductRecord> productList = warehouse.getProductList();
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
      }catch (IndexOutOfBoundsException e){
        return productList;
      }
    }
    return productList;
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

  public int getCount(){
    return warehouse.getProductList().size();
  }
}
