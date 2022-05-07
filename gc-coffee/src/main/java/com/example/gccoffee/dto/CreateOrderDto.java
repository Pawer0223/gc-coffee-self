package com.example.gccoffee.dto;

import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.model.OrderStatus;
import com.example.gccoffee.model.vo.Email;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

import static com.example.gccoffee.dao.orders.OrdersField.*;

public class CreateOrderDto {
    private Email email;
    private String address;
    private String postcode;
    private List<OrderItem> orderItems;

    public CreateOrderDto(Email email, String address, String postcode, List<OrderItem> orderItems) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public SqlParameterSource toParameterSource() {
        return new MapSqlParameterSource()
                .addValue(EMAIL.getCamel(), this.email.getAddress())
                .addValue(ADDRESS.getCamel(), this.address)
                .addValue(POSTCODE.getCamel(), this.postcode)
                .addValue(ORDER_ITEMS.getCamel(), this.orderItems)
                .addValue(ORDER_STATUS.getCamel(), OrderStatus.ACCEPTED.toString());
    }
}
