package com.ikea.inter.product.api.controller;

import com.ikea.inter.product.api.dto.ProductCreateDTO;
import com.ikea.inter.product.api.dto.ProductDTO;
import com.ikea.inter.product.api.dto.ProductListDTO;
import com.ikea.inter.product.api.exception.ProductNotFoundException;
import com.ikea.inter.product.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @Validated
  @PostMapping
  public ResponseEntity<ProductDTO> save(@RequestBody @Valid ProductCreateDTO productCreateDTO) {
    ProductDTO productDTO = productService.save(productCreateDTO);
    return ResponseEntity.ok(productDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id)
      throws ProductNotFoundException {
    ProductDTO productDTO = productService.findById(id);
    return ResponseEntity.ok(productDTO);
  }

  @GetMapping("/{pageNo}/{pageSize}")
  public ResponseEntity<ProductListDTO> list(@PathVariable("pageNo") Integer pageNo,
      @PathVariable("pageSize") Integer pageSize) {
    ProductListDTO productListDTO = productService.list(pageNo, pageSize);
    return ResponseEntity.ok(productListDTO);
  }
}
