package com.example.gccoffee.controller;

import com.example.gccoffee.dto.ViewProductDto;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ViewProductDto> productList(@RequestParam Optional<Category> category) {
        return category.map(productService::getProductByCategory)
                .orElseGet(() -> productService.getAllProducts());
    }
}
