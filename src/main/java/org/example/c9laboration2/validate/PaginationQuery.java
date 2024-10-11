package org.example.c9laboration2.validate;

import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PaginationQuery {

  @QueryParam("productId")
  @DefaultValue("1")
  @Positive
  private long productId;

  @QueryParam("pageSize")
  @DefaultValue("1000")
  @Positive
  private long pageSize;

  @Positive
  public long getPageSize() {
    return pageSize;
  }

  @Positive
  public long getProductId() {
    return productId;
  }
}
