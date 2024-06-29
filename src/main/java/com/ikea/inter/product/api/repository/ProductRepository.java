package com.ikea.inter.product.api.repository;

import com.ikea.inter.product.api.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByNameLikeIgnoreCase(String query);

  @Query("SELECT name from Product")
  List<String> findAllProductNames();
}
