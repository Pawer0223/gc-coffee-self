package com.example.gccoffee.dao.orders;

public enum OrdersField {
    ORDER_ID("orderId", "order_id"),
    EMAIL("email", "email"),
    ADDRESS("address", "address"),
    POSTCODE("postcode", "postcode"),
    ORDER_ITEMS("orderItems", "order_items"),
    ORDER_STATUS("orderStatus", "order_status"),
    CREATED_AT("createdAt", "created_at"),
    UPDATED_AT("updatedAt", "updated_at");

    private final String camel;
    private final String snake;

    OrdersField(String camel, String snake) {
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
