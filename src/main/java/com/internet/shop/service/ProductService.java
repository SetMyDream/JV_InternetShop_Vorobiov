package com.internet.shop.service;

import com.internet.shop.model.Product;

public interface ProductService extends GenericSerivce<Product, Long> {
    Product create(Product product);

    Product update(Product product);
}
