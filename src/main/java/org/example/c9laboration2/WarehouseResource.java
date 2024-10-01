package org.example.c9laboration2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.c9laboration2.entities.ProductRecord;

import java.util.List;

@Path("/warehouse")
public class WarehouseResource {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String hello() {
    return "Hello World";
  }
}