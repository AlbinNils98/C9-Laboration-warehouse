package org.example.c9laboration2.resource;

import jakarta.ws.rs.core.*;
import org.example.c9laboration2.validate.PaginationQuery;
import org.example.c9laboration2.interceptor.Log;
import org.example.c9laboration2.validate.ValidCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;
import org.example.c9laboration2.entities.ProductRecord;
import org.example.c9laboration2.services.WarehouseService;

import java.util.List;
import java.net.URI;
import java.util.Optional;

@Log
@Path("/warehouse")
public class WarehouseResource {
  private WarehouseService warehouseService;
  private static final Logger logger = LoggerFactory.getLogger(WarehouseResource.class);

  @Context
  private UriInfo uriInfo;

  public WarehouseResource() {
  }

  @Inject
  public WarehouseResource(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @GET
  @Path("/products")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllProducts(@BeanParam @Valid PaginationQuery paginationQuery) {
    List<ProductRecord> paginatedProductList = warehouseService.getPaginatedProductList(
        paginationQuery.getProductId(),
        paginationQuery.getPageSize());

    return Response.ok(paginatedProductList).build();
  }

  @GET
  @Path("/products/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProductById(@PathParam("id") String id) {
    Optional<ProductRecord> product = warehouseService.getProductById(id);
    return Response.ok(product).build();
  }

  @GET
  @Path("/products/category/{category}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response searchByCategory(@PathParam("category") @ValidCategory String category) {

    List<ProductRecord> products =
        warehouseService.getProductsByCategory(Category.valueOf(category.toUpperCase()));

    return Response.ok(products).build();
  }

  @POST
  @Path("/products")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addProduct(@Valid Product product, @HeaderParam("X-Forwarded-Proto") String proto) {
    warehouseService.addProduct(product);
    URI location = UriBuilder.fromUri(uriInfo.getBaseUri())
        .path("persons")
        .path(String.valueOf(warehouseService.getCount()))
        .scheme(proto != null ? proto : uriInfo.getRequestUri().getScheme())
        .host(uriInfo.getBaseUri().getHost())
        .port(uriInfo.getBaseUri().getPort())
        .build();

    return Response.created(location).build();
  }

  @POST
  @Path("/populate")
  public Response populate() {
    warehouseService.populateProducts();
    return Response.ok().build();
  }
}