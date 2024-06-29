package com.demo.product.api.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {
  public List<String> getSuggestions(String query,List<String> productNames) {
    List<String> suggestions = new ArrayList<>();
    productNames.parallelStream().forEach(productName -> {
      boolean isRelevant = isRelevant(query, productName.toLowerCase());
      if (isRelevant) {
        suggestions.add(productName);
      }
    });
    return suggestions;
  }

  private boolean isRelevant(String searchText, String productName) {
    boolean isRelevant = false;
    for (String productText : productName.split(" ")) {
      int[][] searchMatrix = new int[searchText.length() + 1][productText.length() + 1];

      // Initialize the table
      for (int i = 0; i <= searchText.length(); i++) {
        searchMatrix[i][0] = i;
      }
      for (int j = 0; j <= productText.length(); j++) {
        searchMatrix[0][j] = j;
      }

      for (int i = 1; i <= searchText.length(); i++) {
        for (int j = 1; j <= productText.length(); j++) {
          char searchChar = searchText.charAt(i - 1);
          char productChar = productText.charAt(j - 1);
          int value = searchMatrix[i - 1][j - 1];
          if (searchChar != productChar) {
            int searchValue = searchMatrix[i - 1][j];
            int productValue = searchMatrix[i][j - 1];
            value = Math.min(Math.min(searchValue, productValue), value) + 1;
          }
          searchMatrix[i][j] = value;
        }
      }

      int distance = searchMatrix[searchText.length()][productText.length()];
      if (distance == 1) {
        isRelevant = true;
        break;
      }
    }
    return isRelevant;
  }
}
