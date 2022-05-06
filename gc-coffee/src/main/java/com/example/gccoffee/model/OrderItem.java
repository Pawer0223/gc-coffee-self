package com.example.gccoffee.model;

public class OrderItem {
    private final Long productId;
    private final Long price;
    private final Integer quantity;

    public OrderItem(Long productId, Long price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
