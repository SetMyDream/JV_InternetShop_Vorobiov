package com.internet.shop.model;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private boolean deleted;

    public Product(Long productId, String name, double price) {
        this.name = name;
        this.id = productId;
        this.price = price;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(Long id, String name, Double price, boolean deleted) {
        this(id, name, price);
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price + "}\n";
    }
}
