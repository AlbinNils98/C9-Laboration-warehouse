package org.example.c9laboration2.exceptionMappers;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.c9laboration2.exceptionmapper.BadRequestExceptionMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BadRequestMapperTest {

  @Test
  public void testToResponse() {
    BadRequestExceptionMapper badRequestExceptionMapper = new BadRequestExceptionMapper();
    BadRequestException exception = new BadRequestException("Invalid request data");

    Response response = badRequestExceptionMapper.toResponse(exception);

    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

    Map<String, Object> responseMap = (Map<String, Object>) response.getEntity();
    assertEquals("Bad request", responseMap.get("error"));
    assertEquals("Invalid request data", responseMap.get("message"));
    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseMap.get("status"));

  }
}