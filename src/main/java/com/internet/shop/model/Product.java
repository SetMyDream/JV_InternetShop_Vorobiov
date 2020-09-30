package com.internet.shop.model;

public class Product {
    private Long productId;
    private String name;
    private Double price;
    private boolean deleted;

    public Product(String name, double price, Long productId, boolean deleted) {
        this.name = name;
        this.price = price;
        this.productId = productId;
        this.deleted = deleted;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nProduct{"
                + "id=" + productId
                + ", name='" + name + '\''
                + ", price=" + price + "}\n";
    }
}
