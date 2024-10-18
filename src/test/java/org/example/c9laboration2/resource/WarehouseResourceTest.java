package org.example.c9laboration2.resource;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.example.c9laboration2.config.CustomJacksonProvider;
import org.example.c9laboration2.entities.Category;
import org.example.c9laboration2.entities.Product;
import org.example.c9laboration2.exceptionmapper.NotFoundExceptionMapper;
import org.example.c9laboration2.mocks.ProductMock;
import org.example.c9laboration2.services.WarehouseService;
import org.example.c9laboration2.exceptionmapper.ConstraintExceptionMapper;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Set;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class WarehouseResourceTest {

  Dispatcher dispatcher;
  WarehouseService service;
  ProductMock product = new ProductMock("PRODUCT_NAME", Category.FURNITURE, 7);

  @BeforeEach
  public void setUp() {
    service = new WarehouseService();
    WarehouseResource warehouseResource = new WarehouseResource(service);
    dispatcher = MockDispatcherFactory.createDispatcher();
    dispatcher.getRegistry().addSingletonResource(warehouseResource);

    CustomJacksonProvider customJacksonProvider = new CustomJacksonProvider();
    dispatcher.getProviderFactory().registerProviderInstance(customJacksonProvider);

    ExceptionMapper<ConstraintViolationException> exMapper =
        new ConstraintExceptionMapper();
    dispatcher.getProviderFactory().registerProviderInstance(exMapper);

    ExceptionMapper<NotFoundException> notFoundMapper = new NotFoundExceptionMapper();
    dispatcher.getProviderFactory().registerProviderInstance(notFoundMapper);
  }

  static void exceptionResponse(String json, String message, String error, int status) throws JSONException {
    JSONObject expectedJSON = new JSONObject(json);
    assertThat(expectedJSON.get("error")).isEqualTo(error);
    assertThat(expectedJSON.get("message")).isEqualTo(message);
    assertThat(expectedJSON.get("status")).isEqualTo(status);
  }


  @Nested
  class GetAllProducts {
    @Test
    @DisplayName("Calling products should return JSON object")
    void callingProductsShouldReturnJSONObject() throws URISyntaxException, UnsupportedEncodingException, JSONException {
      service.addProduct(product);
      MockHttpRequest request = MockHttpRequest.get("/warehouse/products");
      MockHttpResponse response = new MockHttpResponse();
      dispatcher.invoke(request, response);
      String jsonBody = response.getContentAsString();

      assertEquals(200, response.getStatus());

      String expected =
          """
              [{"id":"1","name":"PRODUCT_NAME","category":"FURNITURE","rating":7,"creationDate":"2024-10-09","lastModified":"2024-10-09"}]""";

      JSONArray expectedJSON = new JSONArray(expected);
      JSONArray actualJSON = new JSONArray(jsonBody);
      JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.LENIENT);
    }

    @Test
    @DisplayName("Calling products when there are no products should return NotFoundException as json")
    void callingProductsWhenThereAreNoProductsShouldReturnNotFoundExceptionAsJson() throws URISyntaxException, UnsupportedEncodingException, JSONException {
      MockHttpRequest request = MockHttpRequest.get("/warehouse/products");
      MockHttpResponse response = new MockHttpResponse();
      dispatcher.invoke(request, response);
      String jsonBody = response.getContentAsString();

      exceptionResponse(
          jsonBody,
          "No products in warehouse",
          "Resource not found",
          404);
    }
  }

  @Nested
  class GetProductById {
    @Test
    @DisplayName("Calling products with id that dont exist should return not found")
    void callingProductsWithIdThatDontExistShouldReturnNotFound()
        throws
        URISyntaxException,
        UnsupportedEncodingException, JSONException {
      service.addProduct(product);
      MockHttpRequest request = MockHttpRequest.get("/warehouse/products/2");
      MockHttpResponse response = new MockHttpResponse();
      dispatcher.invoke(request, response);
      String jsonBody = response.getContentAsString();

      exceptionResponse(
          jsonBody,
          "No products found with id: 2",
          "Resource not found",
          404);
    }

    @Test
    @DisplayName("Calling products with id that exist should return a product")
    void callingProductsWithIdThatExistShouldReturnAProduct() throws UnsupportedEncodingException, URISyntaxException, JSONException {
      service.addProduct(product);
      MockHttpRequest request = MockHttpRequest.get("/warehouse/products/1");
      MockHttpResponse response = new MockHttpResponse();
      dispatcher.invoke(request, response);
      String jsonBody = response.getContentAsString();

      assertEquals(200, response.getStatus());

      String expected =
          """
              {"id":"1","name":"PRODUCT_NAME","category":"FURNITURE","rating":7,"creationDate":"2024-10-09","lastModified":"2024-10-09"}""";
      JSONObject expectedJSON = new JSONObject(expected);
      JSONObject actualJSON = new JSONObject(jsonBody);
      JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.LENIENT);
    }
  }

  @Nested
  class GetProductsByCategory {
    @Test
    @DisplayName("Should return products for a category with products")
    void shouldReturnProductsForACategoryWithProducts() throws URISyntaxException, UnsupportedEncodingException, JSONException {
      service.addProduct(product);

      MockHttpRequest request = MockHttpRequest.get("/warehouse/products/category/FURNITURE")
          .accept("application/json");
      MockHttpResponse response = new MockHttpResponse();

      dispatcher.invoke(request, response);

      assertEquals(200, response.getStatus());

      String jsonBody = response.getContentAsString();
      String expectedJson = """
          [{"id":"1","name":"PRODUCT_NAME","category":"FURNITURE","rating":7,"creationDate":"2024-10-09","lastModified":"2024-10-09"}]""";
      JSONArray expected = new JSONArray(expectedJson);
      JSONArray actual = new JSONArray(jsonBody);
      JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
      assertThat(jsonBody).isEqualTo(expectedJson);

      assertEquals("application/json", response.getOutputHeaders().getFirst("Content-Type").toString());
    }

    @Test
    @DisplayName("Should return products in alphabetical order when multiple products exist in a category")
    void shouldReturnProductsInAlphabeticalOrder() throws URISyntaxException, UnsupportedEncodingException, JSONException {
      service.addProduct(product);
      service.addProduct(new ProductMock("Chair", Category.FURNITURE, 5));
      service.addProduct(new ProductMock("Table", Category.FURNITURE, 8));

      MockHttpRequest request = MockHttpRequest.get("/warehouse/products/category/FURNITURE");
      MockHttpResponse response = new MockHttpResponse();

      dispatcher.invoke(request, response);

      assertEquals(200, response.getStatus());

      String jsonBody = response.getContentAsString();
      String expectedJson = """
          [{"id":"2","name":"Chair","category":"FURNITURE","rating":5,"creationDate":"2024-10-09","lastModified":"2024-10-09"},{"id":"1","name":"PRODUCT_NAME","category":"FURNITURE","rating":7,"creationDate":"2024-10-09","lastModified":"2024-10-09"},{"id":"3","name":"Table","category":"FURNITURE","rating":8,"creationDate":"2024-10-09","lastModified":"2024-10-09"}]""";
      JSONArray expected = new JSONArray(expectedJson);
      JSONArray actual = new JSONArray(jsonBody);
      JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
    }

    @Test
    @DisplayName("Should return NotFoundException when no products with category")
    void shouldReturnNotFoundExceptionWhenNoProductsWithCategory() throws URISyntaxException, UnsupportedEncodingException, JSONException {
      MockHttpRequest request = MockHttpRequest.get("/warehouse/products/category/health");
      MockHttpResponse response = new MockHttpResponse();

      dispatcher.invoke(request, response);

      String jsonBody = response.getContentAsString();

      exceptionResponse(
          jsonBody,
          "No products found for category: HEALTH",
          "Resource not found",
          404);
    }
  }

  @Nested
  class PostProduct {
    @Test
    @DisplayName("Should add a product and return 201 Created")
    void shouldAddProductAndReturn201() throws URISyntaxException, JsonProcessingException {
      int size = service.getCount();
      MockHttpRequest request = MockHttpRequest.post("/warehouse/products");
      MockHttpResponse response = new MockHttpResponse();

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

      String json = objectMapper.writeValueAsString(new ProductMock("NEW_PRODUCT", Category.FURNITURE, 1));
      request.content(json.getBytes());
      request.contentType(MediaType.APPLICATION_JSON);

      dispatcher.invoke(request, response);

      assertEquals(201, response.getStatus());
      assertThat(service.getCount()).isEqualTo(size + 1);
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException for invalid product")
    void shouldThrowConstraintViolationExceptionForInvalidProduct() {
      Product invalidProduct = new Product("NEW_PRODUCT", Category.FURNITURE, 0);

      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Validator validator = factory.getValidator();
      Set<ConstraintViolation<Product>> violations = validator.validate(invalidProduct);

      assertFalse(violations.isEmpty());
      assertEquals(1, violations.size());
      assertEquals("Rating must be at least 1", violations.iterator().next().getMessage());
    }
  }
}

