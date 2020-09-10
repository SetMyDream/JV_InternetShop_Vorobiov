package com.internet.shop.service;

import com.internet.shop.model.Product;

public interface ProductService extends GenericService<Product, Long> {
    Product create(Product product);

    Product update(Product product);
}
