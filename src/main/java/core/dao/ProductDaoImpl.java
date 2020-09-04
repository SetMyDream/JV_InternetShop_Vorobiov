package core.dao;

import core.db.Storage;
import core.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> getById(Long productId) {
        return Storage.products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(prod -> Storage.products.get(prod).getId().equals(product.getId()))
                .forEach(prod -> Storage.products.set(prod, product));
        return product;
    }

    @Override
    public boolean deleteById(Long productId) {
        return Storage.products.removeIf(product -> product.getId().equals(productId));
    }

    @Override
    public boolean delete(Product product) {
        return Storage.products.remove(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return Storage.products;
    }
}
