package com.example.gccoffee.model;

public class OrderItem {
    private final Long productId;
    private final Category category;
    private final long price;
    private final int quantity;

    public OrderItem(Long productId, Category category, long price, int quantity) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
