package com.demo.product.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

  private HomeController homeController;

  @BeforeEach
  public void setUp() {
    homeController = new HomeController();
  }

  @Test
  void index_should_redirect_to_swagger() {
    String result = homeController.index();
    assertEquals("redirect:swagger-ui/index.html", result);
  }

}
