package com.example.gccoffee.dto;

import com.example.gccoffee.model.Category;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;

public class CreateProductDto {
    private String productName;
    private Category category;
    private long price;
    private String description;

    public CreateProductDto(String productName, Category category, long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SqlParameterSource toParameterSource() {
        return new MapSqlParameterSource()
                .addValue("productName", this.productName)
                .addValue("category", this.category.toString())
                .addValue("price", this.price)
                .addValue("description", this.description);
    }
}
