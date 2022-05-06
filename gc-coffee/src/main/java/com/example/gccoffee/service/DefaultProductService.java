package com.example.gccoffee.service;

import com.example.gccoffee.dao.products.ProductRepository;
import com.example.gccoffee.dto.CreateProductDto;
import com.example.gccoffee.dto.ViewProductDto;
import com.example.gccoffee.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ViewProductDto> getProductByCategory(Category category) {
        return productRepository.findByCategory(category)
                .stream()
                .map((product) -> ViewProductDto.from(product))
                .collect(toList())
                ;
    }

    @Override
    public List<ViewProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map((product) -> ViewProductDto.from(product))
                .collect(toList());
    }

    @Override
    public Long createProduct(CreateProductDto createProductDto) {
        return productRepository.insert(createProductDto);
    }
}
