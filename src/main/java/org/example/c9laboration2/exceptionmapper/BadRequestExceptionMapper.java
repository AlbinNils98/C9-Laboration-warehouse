package org.example.c9laboration2.exceptionmapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
  private static final Logger logger = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

  @Override
  public Response toResponse(BadRequestException exception) {
    logger.error("Bad request: {}", exception.getMessage());

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("error", "Bad request");
    responseMap.put("message", exception.getMessage());
    responseMap.put("status", Response.Status.BAD_REQUEST.getStatusCode());
    responseMap.put("timestamp", LocalDateTime.now().toString());

    return Response.status(Response.Status.BAD_REQUEST)
        .entity(responseMap)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
