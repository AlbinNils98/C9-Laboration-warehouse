package org.example.c9laboration2.exceptionmapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
  private static final Logger logger = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

  @Override
  public Response toResponse(NotFoundException exception) {
    logger.error("Resource not found: {}", exception.getMessage());

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("error", "Resource not found");
    responseMap.put("message", exception.getMessage());
    responseMap.put("status", Response.Status.NOT_FOUND.getStatusCode());
    responseMap.put("timestamp", LocalDateTime.now().toString());

    return Response.status(Response.Status.NOT_FOUND)
        .entity(responseMap)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
