package com.example.gccoffee.dao.orders;

public enum OrderItemsField {
    ORDER_ID("orderId", "order_id"),
    PRODUCT_ID("productId", "product_id"),
    PRICE("price", "price"),
    QUANTITY("quantity", "quantity"),
    CREATED_AT("createdAt", "created_at"),
    UPDATED_AT("updatedAt", "updated_at");

    private final String camel;
    private final String snake;

    OrderItemsField(String camel, String snake) {
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
