package com.demo.product.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.demo.product.api.dto.ProductDTO;
import com.demo.product.api.exception.ProductNotFoundException;
import com.demo.product.api.service.ProductService;
import com.demo.product.api.dto.ProductCreateDTO;
import com.demo.product.api.dto.ProductListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
  private ProductController productController;
  private ProductService productService;

  @BeforeEach
  public void setUp() {
    productService = mock(ProductService.class);
    productController = new ProductController(productService);
  }

  @Test
  void save_should_call_service_to_persist_product() {
    ProductCreateDTO productCreateDTO = ProductCreateDTO.builder().build();
    ProductDTO productDTO = ProductDTO.builder().build();
    when(productService.save(productCreateDTO)).thenReturn(productDTO);

    ResponseEntity<ProductDTO> response = productController.save(productCreateDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    ProductDTO result = response.getBody();
    assertEquals(result, productDTO);
  }

  @Test
  void findById_should_call_service_to_find_product() throws ProductNotFoundException {
    ResponseEntity<ProductDTO> response = productController.findById(1L);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(productService).findById(1L);
  }

  @Test
  void listProducts_should_call_service_to_find_all_products_with_pagination() {
    ResponseEntity<ProductListDTO> response = productController.list(0, 10);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(productService).list(0, 10);
  }
}
