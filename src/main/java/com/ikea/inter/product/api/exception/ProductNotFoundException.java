package com.ikea.inter.product.api.exception;

import java.io.Serial;

public class ProductNotFoundException extends Exception {
  @Serial
  private static final long serialVersionUID = 1L;

  public ProductNotFoundException(Long id) {
    super("Product not found with id " + id);
  }
}
