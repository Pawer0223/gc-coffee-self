package com.example.gccoffee.service;

import com.example.gccoffee.dto.CreateProductDto;
import com.example.gccoffee.dto.ViewProductDto;
import com.example.gccoffee.model.Category;

import java.util.List;

public interface ProductService {
    List<ViewProductDto> getProductByCategory(Category category);

    List<ViewProductDto> getAllProducts();

    Long createProduct(CreateProductDto createProductDto);
}
