package com.example.gccoffee.controller;

import com.example.gccoffee.dao.products.ProductsField;
import com.example.gccoffee.dto.ViewProductDto;
import com.example.gccoffee.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.gccoffee.model.Category.COFFEE_BEAN_PACKAGE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductRestController.class)
@AutoConfigureMockMvc
class ProductRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    private void fieldValidationInArray(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(jsonPath("$.[0].productId").exists())
                .andExpect(jsonPath("$.[0].productName").exists())
                .andExpect(jsonPath("$.[0].category").exists())
                .andExpect(jsonPath("$.[0].price").exists())
                .andExpect(jsonPath("$.[0].description").exists())
                .andExpect(jsonPath("$.[0].createdAt").exists())
                .andExpect(jsonPath("$.[0].updatedAt").exists());
    }

    @Test
    @DisplayName("모든 상품을 조회할 수 있다.")
    void productsFindAll() throws Exception {

        List<ViewProductDto> products = List.of(
                new ViewProductDto(1L, "prd1", COFFEE_BEAN_PACKAGE, 1000L, "desc", LocalDateTime.now(), LocalDateTime.now()),
                new ViewProductDto(2L, "prd2", COFFEE_BEAN_PACKAGE, 1500L, "desc2", LocalDateTime.now(), LocalDateTime.now()),
                new ViewProductDto(3L, "prd3", COFFEE_BEAN_PACKAGE, 2000L, "desc3", LocalDateTime.now(), LocalDateTime.now())
        );

        given(productService.getAllProducts()).willReturn(products);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
        );

        fieldValidationInArray(resultActions);
        resultActions
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("카테고리를 조건으로 상품리스트 조회할 수 있다.")
    void productsFindAllWithCategory() throws Exception {

        List<ViewProductDto> products = List.of(
                new ViewProductDto(1L, "prd1", COFFEE_BEAN_PACKAGE, 1000L, "desc", LocalDateTime.now(), LocalDateTime.now())
        );

        given(productService.getProductByCategory(COFFEE_BEAN_PACKAGE)).willReturn(products);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/products/")
                .param(ProductsField.CATEGORY.getCamel(), COFFEE_BEAN_PACKAGE.toString())
                .accept(MediaType.APPLICATION_JSON)
        );

        fieldValidationInArray(resultActions);
        resultActions
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }
}