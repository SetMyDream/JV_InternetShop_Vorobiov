package com.internet.shop.dao;

import com.internet.shop.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product create(Product product);

    Optional<Product> getById(Long productId);

    Product update(Product product);

    boolean delete(Long productId);

    List<Product> getAll();
}
