package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long shoppingCartId;
    private Long userId;
    private List<Product> products;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        products = new ArrayList<>();
    }

    public ShoppingCart(Long shoppingCartId, Long userId) {
        this.userId = userId;
        this.shoppingCartId = shoppingCartId;
        products = new ArrayList<>();
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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
        return "ShoppingCart{"
                + "id=" + shoppingCartId + ", userId=" + userId
                + ", products=" + products + '}';
    }
}
