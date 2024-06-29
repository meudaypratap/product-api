package com.ikea.inter.product.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.ikea.inter.product.api.dto.ProductCreateDTO;
import com.ikea.inter.product.api.dto.ProductDTO;
import com.ikea.inter.product.api.dto.ProductListDTO;
import com.ikea.inter.product.api.dto.ProductSearchResultDTO;
import com.ikea.inter.product.api.entity.Product;
import com.ikea.inter.product.api.exception.ProductNotFoundException;
import com.ikea.inter.product.api.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
  private ProductService productService;
  private ProductRepository productRepository;
  private SuggestionService suggestionService;

  @BeforeEach
  public void setUp() {
    suggestionService = mock(SuggestionService.class);
    productRepository = mock(ProductRepository.class);
    productService = new ProductService(productRepository, suggestionService);
  }

  @Test
  void save_product_should_persist_product() {
    Double price = Double.valueOf("2");
    String name = "Test";
    String description = "Test description";
    String category = "Test category";
    String imageUrl = "http://image.jpg";
    ProductCreateDTO productCreateDTO = ProductCreateDTO.builder()
        .price(price)
        .name(name)
        .category(category)
        .description(description)
        .imageUrl(imageUrl)
        .build();

    ProductDTO result = productService.save(productCreateDTO);

    ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
    verify(productRepository).save(productArgumentCaptor.capture());
    Product product = productArgumentCaptor.getValue();
    assertEquals(product.getCategory(), result.getCategory());
    assertEquals(product.getDescription(), result.getDescription());
    assertEquals(product.getImageUrl(), result.getImageUrl());
    assertEquals(product.getName(), result.getName());
    assertEquals(product.getPrice(), result.getPrice());
  }

  @Test
  void findById_should_find_product_from_repository() throws ProductNotFoundException {
    Long id = 1L;
    Double price = Double.valueOf("2");
    String name = "Test";
    String description = "Test description";
    String category = "Test category";
    String imageUrl = "http://image.jpg";
    Product product = Product.builder()
        .id(id)
        .price(price)
        .name(name)
        .category(category)
        .description(description)
        .imageUrl(imageUrl)
        .build();
    when(productRepository.findById(id)).thenReturn(Optional.of(product));
    ProductDTO result = productService.findById(id);
    assertEquals(product.getCategory(), result.getCategory());
    assertEquals(product.getDescription(), result.getDescription());
    assertEquals(product.getImageUrl(), result.getImageUrl());
    assertEquals(product.getName(), result.getName());
    assertEquals(product.getPrice(), result.getPrice());
    assertEquals(product.getId(), result.getId());
  }

  @Test
  void findById_should_find_product_from_repository_should_throw_product_not_found_exception_when_product_is_not_found() {
    Long id = 1L;
    ProductNotFoundException exception =
        assertThrows(ProductNotFoundException.class, () -> productService.findById(id));

    verify(productRepository).findById(id);
    assertEquals(exception.getMessage(), "Product not found with id " + id);
  }

  @Test
  void list_should_return_paginated_products() {
    int pageNo = 0;
    int pageSize = 10;
    PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
    Long id = 1L;
    Double price = Double.valueOf("2");
    String name = "Test";
    String description = "Test description";
    String category = "Test category";
    String imageUrl = "http://image.jpg";
    Product product = Product.builder()
        .id(id)
        .price(price)
        .name(name)
        .category(category)
        .description(description)
        .imageUrl(imageUrl)
        .build();
    Page<Product> products = new PageImpl(List.of(product), pageRequest, 1);
    when(productRepository.findAll(pageRequest)).thenReturn(products);

    ProductListDTO result = productService.list(pageNo, pageSize);

    assertEquals(1, result.getTotal());
    assertEquals(1, result.getContent().size());
    ProductDTO productDTO = result.getContent().get(0);
    assertEquals(product.getCategory(), productDTO.getCategory());
    assertEquals(product.getDescription(), productDTO.getDescription());
    assertEquals(product.getImageUrl(), productDTO.getImageUrl());
    assertEquals(product.getName(), productDTO.getName());
    assertEquals(product.getPrice(), productDTO.getPrice());
    assertEquals(product.getId(), productDTO.getId());
  }

  @Test
  void search_should_return_result_from_repository_and_suggestions_not_added() {
    String query = "sofa";
    Long id = 1L;
    Double price = Double.valueOf("2");
    String name = "Test";
    String description = "Test description";
    String category = "Test category";
    String imageUrl = "http://image.jpg";
    Product product = Product.builder()
        .id(id)
        .price(price)
        .name(name)
        .category(category)
        .description(description)
        .imageUrl(imageUrl)
        .build();
    List<Product> products = List.of(product);
    when(productRepository.findAllByNameLikeIgnoreCase("%" + query + "%")).thenReturn(products);

    ProductSearchResultDTO result = productService.search(query);
    assertEquals(1, result.getContent().size());
    ProductDTO productDTO = result.getContent().get(0);
    assertEquals(product.getCategory(), productDTO.getCategory());
    assertEquals(product.getDescription(), productDTO.getDescription());
    assertEquals(product.getImageUrl(), productDTO.getImageUrl());
    assertEquals(product.getName(), productDTO.getName());
    assertEquals(product.getPrice(), productDTO.getPrice());
    assertEquals(product.getId(), productDTO.getId());
    assertEquals(1, result.getTotal());
    assertEquals(0, result.getSuggestions().size());
    verifyNoInteractions(suggestionService);
    verify(productRepository, never()).findAllProductNames();
  }

  @Test
  void search_should_call_suggestions_when_no_result_from_repository() {
    String query = "sofu";
    List<String> productNames = List.of("3 seat sofa", "car track");
    when(productRepository.findAllByNameLikeIgnoreCase("%" + query + "%")).thenReturn(
        new ArrayList<>());
    when(productRepository.findAllProductNames()).thenReturn(productNames);
    when(suggestionService.getSuggestions(query, productNames)).thenReturn(List.of("3 seat sofa"));

    ProductSearchResultDTO result = productService.search(query);
    assertEquals(0, result.getContent().size());
    assertEquals(0, result.getTotal());
    assertEquals(List.of("3 seat sofa"), result.getSuggestions());
  }

  @Test
  void bootstrap() {
    productService.bootstrap();
    ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
    verify(productRepository).saveAll(argumentCaptor.capture());
    assertEquals(6, argumentCaptor.getValue().size());
  }
}
