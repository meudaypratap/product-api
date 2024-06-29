package com.ikea.inter.product.api.converter;

import com.ikea.inter.product.api.dto.ProductCreateDTO;
import com.ikea.inter.product.api.dto.ProductDTO;
import com.ikea.inter.product.api.entity.Product;

public class ProductConverter {

  public static Product convert(ProductCreateDTO productCreateDTO) {
    return Product.builder()
        .price(productCreateDTO.getPrice())
        .category(productCreateDTO.getCategory())
        .description(productCreateDTO.getDescription())
        .name(productCreateDTO.getName())
        .imageUrl(productCreateDTO.getImageUrl())
        .build();
  }

  public static ProductDTO convert(Product product) {
    return ProductDTO.builder()
        .id(product.getId())
        .price(product.getPrice())
        .category(product.getCategory())
        .description(product.getDescription())
        .name(product.getName())
        .imageUrl(product.getImageUrl())
        .build();
  }
}
