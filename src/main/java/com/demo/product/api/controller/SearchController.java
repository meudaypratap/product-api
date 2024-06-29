package com.demo.product.api.controller;

import com.demo.product.api.dto.ProductSearchResultDTO;
import com.demo.product.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
  private final ProductService productService;

  @Validated
  @GetMapping
  public ResponseEntity<ProductSearchResultDTO> search(@RequestParam("query") @Valid String query) {
    query = query == null ? "" : query.trim().toLowerCase();
    return query.isEmpty()
        ? ResponseEntity.badRequest().build()
        : ResponseEntity.ok(productService.search(query));
  }
}
