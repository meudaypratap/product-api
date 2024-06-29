package com.demo.product.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.demo.product.api.dto.ProductSearchResultDTO;
import com.demo.product.api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {
  private SearchController searchController;
  private ProductService productService;

  @BeforeEach
  public void setUp() {
    productService = mock(ProductService.class);
    searchController = new SearchController(productService);
  }

  @Test
  void search_should_call_product_service_for_valid_input() {
    ResponseEntity<ProductSearchResultDTO> response = searchController.search("1234");
    verify(productService).search("1234");
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  "})
  void search_should_not_call_product_service_for_empty_query(String query) {
    ResponseEntity<ProductSearchResultDTO> response = searchController.search(query);
    verifyNoInteractions(productService);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void search_should_not_call_product_service_for_null_query() {
    ResponseEntity<ProductSearchResultDTO> response = searchController.search(null);
    verifyNoInteractions(productService);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

}
