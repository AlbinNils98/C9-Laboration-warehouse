package org.example.c9laboration2;

import jakarta.ws.rs.core.*;
import org.example.c9laboration2.interceptor.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.validation.ConstraintViolationException;
import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;
import org.example.c9laboration2.entities.ProductRecord;
import org.example.c9laboration2.service.WarehouseService;
import java.util.List;
import java.net.URI;

@Log
@Path("/warehouse")
public class WarehouseResource {
  private WarehouseService warehouseService;
  private static final Logger logger = LoggerFactory.getLogger(WarehouseResource.class);

  @Context
  private UriInfo uriInfo;

  public WarehouseResource() {}

  @Inject
  public WarehouseResource(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @GET
  @Path("/products")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllProducts(@BeanParam @Valid PaginationQuery paginationQuery) {

    try {
      List<ProductRecord> paginatedProductList = warehouseService.getPaginatedProductList(
          paginationQuery.getProductId(),
          paginationQuery.getPageSize());

      return Response.ok(paginatedProductList).build();
    } catch (ConstraintViolationException e) {
      logger.error("Validation error: {}", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Validation error: " + e.getMessage())
          .build();
    } catch (Exception e) {
      logger.error("Internal server error: {}", e.getMessage());
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(List.of("Internal server error"))
          .build();
    }
  }

  @GET
  @Path("/products/product/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProductById(@PathParam("id") String id) {
    ProductRecord product = warehouseService.getProductById(id);
    if (product == null) {
      logger.error("No product found with id {}", id);
      return Response.status(Response.Status.NOT_FOUND)
          .entity(List.of("No product found with id " + id))
          .build();
    }
    return Response.ok(product).build();
  }

  @GET
  @Path("/products/category/{category}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response searchByCategory(@PathParam("category") @ValidCategory String category) {

    List<ProductRecord> products =
        warehouseService.getProductsByCategory(Category.valueOf(category.toUpperCase()));

    if (products.isEmpty()) {
      logger.error("No products found with category {}", category);
      return Response.status(Response.Status.OK)
          .entity(List.of("No products found with category " + category))
          .build();
    }
   return Response.ok(products).build();
  }

  @POST
  @Path("/products")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addProduct(@Valid Product product, @HeaderParam("X-Forwarded-Proto") String proto) {
    try {
      warehouseService.addProduct(product);
      URI location = UriBuilder.fromUri(uriInfo.getBaseUri())
          .path("persons")
          .path(String.valueOf(warehouseService.getCount()))
          .scheme(proto != null ? proto : uriInfo.getRequestUri().getScheme())
          .host(uriInfo.getBaseUri().getHost())
          .port(uriInfo.getBaseUri().getPort())
          .build();

      return Response.created(location).build();
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
}