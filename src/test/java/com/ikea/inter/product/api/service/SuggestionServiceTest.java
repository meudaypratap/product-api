package com.ikea.inter.product.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SuggestionServiceTest {
  private SuggestionService suggestionService;

  @BeforeEach
  public void setUp() {
    suggestionService = new SuggestionService();
  }

  @Test
  void getSuggestions() {
    String query = "sofu";
    List<String> productNames = List.of("3 seat sofa", "RÖDALM", "BILLY");
    List<String> suggestions = suggestionService.getSuggestions(query, productNames);
    assertEquals(List.of("3 seat sofa"), suggestions);
  }

}
