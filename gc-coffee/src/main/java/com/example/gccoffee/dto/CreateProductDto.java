package com.example.gccoffee.dto;

import com.example.gccoffee.model.Category;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import static com.example.gccoffee.dao.products.ProductsField.*;

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
                .addValue(PRODUCT_NAME.getCamel(), this.productName)
                .addValue(CATEGORY.getCamel(), this.category.toString())
                .addValue(PRICE.getCamel(), this.price)
                .addValue(DESCRIPTION.getCamel(), this.description);
    }
}
