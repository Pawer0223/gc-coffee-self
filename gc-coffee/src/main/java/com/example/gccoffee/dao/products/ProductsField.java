package com.example.gccoffee.dao.products;

public enum ProductsField {

     PRODUCT_ID("productId", "product_id"),
     PRODUCT_NAME("productName", "product_name"),
     CATEGORY("category", "category"),
     PRICE("price", "price"),
     DESCRIPTION("description", "description"),
     CREATED_AT("createdAt", "created_at"),
     UPDATED_AT("updatedAt", "updated_at");

    private final String camel;
    private final String snake;

    ProductsField(String camel, String snake) {
        this.camel = camel;
        this.snake = snake;
    }

    public String getCamel() {
        return camel;
    }

    public String getSnake() {
        return snake;
    }
}
