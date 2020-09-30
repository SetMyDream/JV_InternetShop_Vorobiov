package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private Long userId;
    private List<Product> products;

    public Order(Long userId) {
        this.userId = userId;
        products = new ArrayList<>();
    }

    public Order(Long orderId, Long userId) {
        this.orderId = orderId;
        this.userId = userId;
        products = new ArrayList<>();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + orderId
                + ", userId=" + userId
                + ", products=" + products + '}';
    }
}
