package com.example.gccoffee.dto;

import com.example.gccoffee.model.Category;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;

public class UpdateProductDto {
    private Long productId;
    private String productName;
    private Category category;
    private long price;
    private String description;

    public UpdateProductDto(Long productId, String productName, Category category, long price, String description) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Map<String, Object> toParamMap() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("productId", this.productId);
        paramMap.put("productName", this.productName);
        paramMap.put("category", this.category.toString());
        paramMap.put("price", this.price);
        paramMap.put("description", this.description);
        return paramMap;
    }


}
