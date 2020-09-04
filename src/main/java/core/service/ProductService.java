package core.service;

import core.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product getById(Long productId);

    Product update(Product product);

    boolean delete(Product product);

    boolean deleteById(Long productId);

    List<Product> getAll();
}
