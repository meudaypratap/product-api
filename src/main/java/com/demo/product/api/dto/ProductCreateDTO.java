package com.demo.product.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {
  @NotNull
  private String name;
  private String description;
  @NotNull
  private String imageUrl;
  @NotNull
  private String category;
  @Min(1)
  @NotNull
  private Double price;
}
