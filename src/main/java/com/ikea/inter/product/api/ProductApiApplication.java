package com.ikea.inter.product.api;

import com.ikea.inter.product.api.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductApiApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ProductService productService) {
    return args -> productService.bootstrap();
  }
}
