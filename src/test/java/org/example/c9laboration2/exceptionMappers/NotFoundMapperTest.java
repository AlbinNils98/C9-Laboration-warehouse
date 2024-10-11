package org.example.c9laboration2.exceptionMappers;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.c9laboration2.exceptionmapper.NotFoundExceptionMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundMapperTest {

  @Test
  public void testToResponse() {

    NotFoundExceptionMapper notFoundExceptionMapper = new NotFoundExceptionMapper();
    NotFoundException exception = new NotFoundException("Requested resource was not found");

    Response response = notFoundExceptionMapper.toResponse(exception);

    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

    Map<String, Object> responseMap = (Map<String, Object>) response.getEntity();
    assertEquals("Resource not found", responseMap.get("error"));
    assertEquals("Requested resource was not found", responseMap.get("message"));
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseMap.get("status"));
  }
}