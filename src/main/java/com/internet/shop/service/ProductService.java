package com.internet.shop.service;

import com.internet.shop.model.Product;
import java.util.List;

public interface ProductService extends GenericSerivce<Product, Long> {
    Product create(Product product);

    Product update(Product product);
}
