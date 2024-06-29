package com.demo.product.api.service;

import com.demo.product.api.dto.ProductDTO;
import com.demo.product.api.dto.ProductSearchResultDTO;
import com.demo.product.api.repository.ProductRepository;
import com.demo.product.api.converter.ProductConverter;
import com.demo.product.api.dto.ProductCreateDTO;
import com.demo.product.api.dto.ProductListDTO;
import com.demo.product.api.entity.Product;
import com.demo.product.api.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;
  private final SuggestionService suggestionService;

  public ProductDTO save(ProductCreateDTO productCreateDTO) {
    Product product = ProductConverter.convert(productCreateDTO);
    productRepository.save(product);
    return ProductConverter.convert(product);
  }

  public ProductDTO findById(Long id) throws ProductNotFoundException {
    Product product =
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    return ProductConverter.convert(product);
  }

  public ProductListDTO list(Integer pageNo, Integer pageSize) {
    pageSize = Math.min(pageSize, 100);
    PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
    Page<Product> products = productRepository.findAll(pageRequest);
    Long total = products.getTotalElements();
    List<ProductDTO> content =
        products.getContent().stream().map(ProductConverter::convert).toList();
    return ProductListDTO.builder().total(total).content(content).build();
  }

  public ProductSearchResultDTO search(String query) {
    List<ProductDTO> content = productRepository.findAllByNameLikeIgnoreCase("%" + query + "%")
        .stream()
        .map(ProductConverter::convert)
        .toList();
    List<String> suggestion = content.isEmpty() ? getSuggestions(query) : new ArrayList<>();
    return ProductSearchResultDTO.builder()
        .total((long) content.size())
        .content(content)
        .suggestions(suggestion)
        .build();
  }

  private List<String> getSuggestions(String query) {
    List<String> productNames = productRepository.findAllProductNames();
    return suggestionService.getSuggestions(query, productNames);
  }

  public void bootstrap() {
    Product product1 = create("3 seat sofa", "Tresund light beige",
        "https://www.ikea.com/se/en/images/products/kivik-3-seat-sofa-tresund-light"
            + "-beige__1124110_pe875021_s5.jpg", "Sofas", "5495");
    Product product2 = create("RÖDALM", "Frame, black, 30x40 cm",
        "https://www.ikea.com/se/en/images/products/roedalm-frame-black__1251232_pe924196_s5.jpg",
        "Wall Decor", "79");
    Product product3 = create("BILLY", "Bookcase, 80x28x202 cm",
        "https://www.ikea.com/se/en/images/products/billy-bookcase-white__1051924_pe845813_s5.jpg"
            + "-beige__1124110_pe875021_s5.jpg", "Bookcases & shelving units", "599");
    Product product4 = create("FÖRDELAKTIG", "Induction hob/integrated extractor, 83 cm",
        "https://www.ikea.com/se/en/images/products/foerdelaktig-induction-hob-integrated"
            + "-extractor-ikea-700-black__0753356_pe747606_s5.jpg", "Appliances", "15995");
    Product product5 = create("SÄBÖVIK", "Divan bed, 140x200 cm",
        "https://www.ikea.com/se/en/images/products/saeboevik-divan-bed-firm-vissle"
            + "-grey__1101350_pe866612_s5.jpg", "Beds", "3795");
    Product product6 = create("RÄVUNGE", "23-piece car track set",
        "https://www.ikea.com/se/en/images/products/raevunge-23-piece-car-track"
            + "-set__1126284_pe875672_s5.jpg", "Children", "99");
    productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));
    log.info(">>>>>>>>>>>>>>>>Bootstrapped Initial data<<<<<<<<<<<<<<<<<<<<<");
  }

  private Product create(String name, String description, String imageUrl, String category,
      String price) {
    return Product.builder()
        .name(name)
        .description(description)
        .imageUrl(imageUrl)
        .category(category)
        .price(Double.valueOf(price))
        .build();
  }
}
