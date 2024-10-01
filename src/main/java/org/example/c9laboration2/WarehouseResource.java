package org.example.c9laboration2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.ConstraintViolationException;
import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;
import org.example.c9laboration2.entities.ProductRecord;
import org.example.c9laboration2.service.Warehouse;
import org.example.c9laboration2.service.WarehouseService;
import javax.print.attribute.standard.Media;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;


@Path("/warehouse")
public class WarehouseResource {
  private WarehouseService warehouseService;
  private static final Logger logger = LoggerFactory.getLogger(WarehouseResource.class);


  public WarehouseResource() {
  }

  @Inject
  public WarehouseResource(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @GET
  @Path("/products")
  @Produces(MediaType.APPLICATION_JSON)
  public Response allProducts(@QueryParam("productIndex") String productId, @QueryParam("pageSize") String pageSize) {

    List<ProductRecord> allProducts = warehouseService.getAllProducts();

  if (productId == null && pageSize == null) {
    return Response.ok(allProducts).build();
  }else {
    List<ProductRecord> paginatedProductList = warehouseService.getProductsByPage(productId, pageSize);
    if (paginatedProductList.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity(List.of("No products found")).build();
    }else {
      return Response.ok(paginatedProductList).build();
    }
  }
  }


  @GET
  @Path("/products/product/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProductById(@PathParam("id") String id) {
    ProductRecord product = warehouseService.getProductById(id);
    if (product == null) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(List.of("No product found with id " + id))
          .build();
    }
    return Response.ok(product).build();
  }

  @GET
  @Path("/products/category/{category}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response searchByCategory(@PathParam("category") String category) {
    if (category == null || !enumChecker(category)) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(List.of("Category path parameter is required and should be valid."))
          .build();
    }
    List<ProductRecord> products =
        warehouseService.getProductsByCategory(Category.valueOf(category.toUpperCase()));

    if (products.isEmpty()) {
      return Response.status(Response.Status.NO_CONTENT).entity(List.of("No products found with category " + category)).build();
    }

   return Response.ok(products).build();
  }

  @POST
  @Path("/products")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addProduct(@Valid Product product) {
    try {
      warehouseService.addProduct(product);
      return Response.status(Response.Status.CREATED).entity(product).build();
    } catch (ConstraintViolationException e) {
      logger.error("Validation error: {}", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Validation error: " + e.getMessage())
          .build();
    } catch (Exception e) {
      logger.error("Internal server error: {}", e.getMessage());
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("Internal server error")
          .build();
    }
  }

  private static boolean enumChecker(String string) {
    return Arrays.stream(Category.values()).anyMatch(categoryEnum -> categoryEnum.toString().equalsIgnoreCase(string));
  }

}