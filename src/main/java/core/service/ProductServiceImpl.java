package core.service;

import core.dao.ProductDao;
import core.lib.Inject;
import core.lib.Service;
import core.model.Product;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product getById(Long productId) {
        return productDao.getById(productId).get();
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(Product product) {
        return productDao.delete(product);
    }

    @Override
    public boolean deleteById(Long productId) {
        return productDao.deleteById(productId);
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAllProducts();
    }
}
