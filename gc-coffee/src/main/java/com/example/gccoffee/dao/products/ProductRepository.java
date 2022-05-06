package com.example.gccoffee.dao.products;

import com.example.gccoffee.dto.CreateProductDto;
import com.example.gccoffee.dto.UpdateProductDto;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Long insert(CreateProductDto product);

    void update(UpdateProductDto product);

    Optional<Product> findById(Long productId);

    Optional<Product> findByName(String productName);

    List<Product> findByCategory(Category category);
}
